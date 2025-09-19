package com.example.e_commerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldCreateAndFetchProduct() {

        var req = """
    {
      "name": "Test Product",
      "netPrice": "2000",
      "vatRate": "0.23"
    }
    """;

        var create = webTestClient.post().uri("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();


    }
    @Test
    void shouldRejectDuplicateName() {
        var req = """
    {
      "name": "Opona 205/55 R16",
      "netPrice": 200.00,
      "vatRate": 0.23
    }
    """;

        webTestClient.post()
                .uri("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();

        webTestClient.post()
                .uri("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req.replace("200.00", "199.99"))
                .exchange()
                .expectStatus().isEqualTo(400);
    }
}
