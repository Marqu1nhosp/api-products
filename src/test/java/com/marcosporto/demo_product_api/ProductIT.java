package com.marcosporto.demo_product_api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcosporto.demo_product_api.web.dto.ProductCreateDto;
import com.marcosporto.demo_product_api.web.dto.ProductResponseDto;
import com.marcosporto.demo_product_api.web.dto.ProductUpdateDto;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/products/products-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/products/products-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void shouldReturnStatus201WhenCreatingProduct() {
        ProductResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDto("teste de produto", "descrição do produto", 500.00))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("teste de produto");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("descrição do produto");
        org.assertj.core.api.Assertions.assertThat(responseBody.getPrice()).isEqualTo(500.00);
    }

    @Test
    public void shouldReturnStatus422WhenCreatingProductWithInvalidData() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDto("", "", 500.00))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void shouldReturnStatus422WhenCreatingProductWithNameLessThan3haracters() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDto("te", "descrição do produto", 500.00))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void shouldReturnStatus422WhenCreatingProductWithPriceNegative() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDto("te", "descrição do produto", -10.00))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void shouldReturnResponse200ProductWhenFetchingById() {
        ProductResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/products/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("teste produto");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("descrição do produto");
        org.assertj.core.api.Assertions.assertThat(responseBody.getPrice()).isEqualTo(50.00);
    }

    @Test
    public void shouldReturnResponse404ProductNotFound() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/products/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void shouldReturnResponse204DeleteProduct() {
        testClient
                .delete()
                .uri("/api/v1/products/100")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void shouldReturnResponse404DeleteProduct() {
        testClient
                .delete()
                .uri("/api/v1/products/0")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void shouldReturnResponse200ListAllProducts() {
        List<ProductResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotEmpty();
    }

    @Test
    public void shouldReturnResponse200ToChangeProduct() {
        testClient
                .put()
                .uri("/api/v1/products/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductUpdateDto("teste123", "teste descrição", 55.00))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void shouldReturnResponse404ToChangeProductNotFound() {
        testClient
                .put()
                .uri("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductUpdateDto("teste123", "teste descrição", 55.00))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
