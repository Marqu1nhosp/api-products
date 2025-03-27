package com.marcosporto.demo_product_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcosporto.demo_product_api.web.dto.CategoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.CategoryResponseDto;
import com.marcosporto.demo_product_api.web.dto.CategoryUpdateDto;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/categories/categories-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/categories/categories-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class CategoryIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void shouldReturnStatus201WhenCreatingCategory() {
        CategoryResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                        "123456"))
                .bodyValue(new CategoryCreateDto("teste de categoria"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("teste de categoria");

    }

    @Test
    public void shouldReturnStatus422WhenCreatingCategoryWithInvalidData() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryCreateDto(""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void shouldReturnResponse200PCategoryWhenFetchingById() {
        CategoryResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/categories/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("teste categoria");
    }

    @Test
    public void shouldReturnResponse404CategoryNotFound() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void shouldReturnResponse204DeleteCategory() {
        testClient
                .delete()
                .uri("/api/v1/categories/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void shouldReturnResponse404DeleteCategory() {
        testClient
                .delete()
                .uri("/api/v1/products/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void shouldReturnResponse200ToChangeCategory() {
        testClient
                .put()
                .uri("/api/v1/categories/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryUpdateDto("teste123"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void shouldReturnResponse404ToChangeCategoryNotFound() {
        testClient
                .put()
                .uri("/api/v1/categories/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryUpdateDto("teste123"))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
