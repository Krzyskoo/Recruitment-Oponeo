package com.example.e_commerce;

import com.example.e_commerce.model.customer.Address;
import com.example.e_commerce.model.customer.PersonCustomer;
import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.repo.CustomerRepo;
import com.example.e_commerce.repo.OrderRepo;
import com.example.e_commerce.repo.ProductRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class OrderIntegrationTest {
    @Autowired WebTestClient webTestClient;
    @Autowired ObjectMapper objectMapper;

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    OrderRepo orderRepo;

    @BeforeEach
    void clean() {
        orderRepo.deleteAll();
        productRepo.deleteAll();
        customerRepo.deleteAll();
    }

    private long saveProduct(String name, double netPrice, double vatRate) {
        Product p = new Product();
        p.setName(name);
        p.setNetPrice(new BigDecimal(String.valueOf(netPrice)));
        p.setVatRate(new BigDecimal(String.valueOf(vatRate)));
        return productRepo.save(p).getId();
    }

    private long savePersonCustomer(String email) {
        PersonCustomer pc = new PersonCustomer();
        pc.setEmail(email);
        pc.setFirstName("Ala");
        pc.setLastName("Makota");
        Address addr = new Address();
        addr.setLine1("Długa 1");
        addr.setCity("Warszawa");
        addr.setZip("00-001");
        addr.setCountry("PL");
        pc.setBillingAddress(addr);
        return customerRepo.save(pc).getId();
    }
    @Test
    void shouldCreateOrderAndFetchIt() throws Exception {
        // given
        long customerId = savePersonCustomer("ala.orders@test");
        long p1 = saveProduct("Komplet A", 200.00, 0.23);
        long p2 = saveProduct("Komplet B", 100.00, 0.23);

        var orderReq = """
        {
          "customerId": %d,
          "items": [
            { "productId": %d, "quantity": 2 },
            { "productId": %d, "quantity": 1 }
          ]
        }
        """.formatted(customerId, p1, p2);

        // when
        var create = webTestClient.post()
                .uri("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderReq)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult();

        JsonNode created = objectMapper.readTree(create.getResponseBody());
        String orderId = created.get("id").asText();

        // then
        var get = webTestClient.get()
                .uri("/api/v1/order/{id}", orderId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult();

        String body = new String(get.getResponseBody());
        JSONAssert.assertEquals("""
        {
          "totalNet": 500.00,
          "totalGross": 615.00,
          "items": [
            {"quantity": 2},
            {"quantity": 1}
          ]
        }
        """, body, false);
    }

    @Test
    void shouldReturn404WhenOrderNotFound() {
        webTestClient.get()
                .uri("/api/v1/order/{id}", "01HZZZZZZZZZZZZZZZZZZZZZZZ")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldFailWhenProductMissing() {
        long customerId = savePersonCustomer("missing.product@test");

        var bad = """
        {
          "customerId": %d,
          "items": [
            { "productId": 999999999, "quantity": 1 }
          ]
        }
        """.formatted(customerId);

        webTestClient.post()
                .uri("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bad)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void shouldFailWhenCustomerMissing() {
        long p = saveProduct("Komplet X", 50.00, 0.23);

        var bad = """
        {
          "customerId": 999999999,
          "items": [ { "productId": %d, "quantity": 1 } ]
        }
        """.formatted(p);

        webTestClient.post()
                .uri("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bad)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void shouldBeAtomicWhenSecondItemInvalid() {
        long customerId = savePersonCustomer("atomic@test");
        long goodProduct = saveProduct("Good", 10.00, 0.23);
        long missingProduct = 9_999_999L;

        var badOrder = """
        {
          "customerId": %d,
          "items": [
            { "productId": %d, "quantity": 1 },
            { "productId": %d, "quantity": 1 }
          ]
        }
        """.formatted(customerId, goodProduct, missingProduct);

        webTestClient.post()
                .uri("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(badOrder)
                .exchange()
                .expectStatus().is4xxClientError();

        assertThat(orderRepo.count()).isZero();
        assertThat(orderRepo.findAll()).extracting(Order::getId).isEmpty();
    }
}
