package com.marcosporto.demo_product_api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcosporto.demo_product_api.web.dto.UserCreateDto;
import com.marcosporto.demo_product_api.web.dto.UserResponseDto;
import com.marcosporto.demo_product_api.web.dto.UserUpdatePasswordDto;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class UsersIT {
        @Autowired
        WebTestClient testClient;

        @Test
        public void shouldReturnCreatedUserWithValidUsernameAndPasswordStatus201() {
                UserResponseDto responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@gmail.com", "123456"))
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(UserResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername())
                                .isEqualTo("marcosteste@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
        }

        @Test
        public void shouldReturnErrorMessageWithInvalidUsernameStatus422() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@email.", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        }

        @Test
        public void shouldReturnErrorMessageWithStatus422_WhenPasswordIsInvalid() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@hotmail.com", ""))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@hotmail.com", "1234"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("marcosteste@hotmail.com", "123456789"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        }

        @Test
        public void shouldReturnErrorMessageWithDuplicateUsernameStatus409() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserCreateDto("ana@gmail.com", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(409)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
        }

        @Test
        public void shouldReturnUserWithStatus200_WhenIdExists() {
                UserResponseDto responseBody = testClient
                                .get()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

                responseBody = testClient
                                .get()
                                .uri("/api/v1/users/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("marcos@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");

                responseBody = testClient
                                .get()
                                .uri("/api/v1/users/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marcos@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UserResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("marcos@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
        }

        @Test
        public void shouldReturnErrorMessageWithStatus400_WhenPasswordIsInvalid() {
                ErrorMessage responseBody = testClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserUpdatePasswordDto("123456", "123456", "000000"))
                                .exchange()
                                .expectStatus().isEqualTo(400)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

                responseBody = testClient
                                .patch()
                                .uri("/api/v1/users/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UserUpdatePasswordDto("458797", "123456", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(400)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
        }

        @Test
        public void shouldReturnAllUsersWithStatus200() {
                List<UserResponseDto> responseBody = testClient
                                .get()
                                .uri("/api/v1/users")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBodyList(UserResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
        }

}
