package com.example.e_commerce;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class CustomerIntegrationTest {

    @Autowired WebTestClient webTestClient;
    @Autowired ObjectMapper objectMapper;

    @Test
    void shouldCreatePersonAndFetchIt() throws Exception {
        var req = """
                {
                  "kind": "COMPANY",
                   "companyName": "ACME Sp. z o.o.",
                   "taxId": "PL1234567890",
                   "email": "office@acme.pl",
                                "phone": "+48221234567",
                                "billingAddress": {
                                  "line1": "ul. Firmowa 10",
                                  "city": "Kraków",
                                  "zip": "30-001",
                                  "country": "PL"
                                },
                                "shippingAddress": {
                                  "line1": "ul. Magazynowa 22",
                                  "city": "Kraków",
                                  "zip": "30-002",
                                  "country": "PL"
                                }

                }
                """;

        var created = webTestClient.post()
                .uri("/api/v1/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult();

        var json = new String(created.getResponseBodyContent());
        var id = objectMapper.readTree(json).get("id").asLong();

        webTestClient.get()
                .uri("/api/v1/customer/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.email").isEqualTo("office@acme.pl");

        JSONAssert.assertEquals("""
    { "kind": "COMPANY", "displayName": "ACME Sp. z o.o." }
    """, json, false);
    }

    @Test
    void shouldRejectDuplicateTaxIdForCompany() {
        var req = """
    {
      "kind": "PERSON",
      "firstName": "Andrzej",
      "lastName": "Nowak",
      "email": "acme@test.it",
      "phone": "+48221234567",
      "billingAddress": { "line1": "Firmowa", "city": "Kraków", "zip": "30-001", "country": "PL" }
    }
    """;

        webTestClient.post().uri("/api/v1/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();

        webTestClient.post().uri("/api/v1/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isEqualTo(400);
    }
}
