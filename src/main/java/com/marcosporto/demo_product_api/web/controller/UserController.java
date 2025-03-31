package com.marcosporto.demo_product_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosporto.demo_product_api.entity.User;
import com.marcosporto.demo_product_api.service.UserService;
import com.marcosporto.demo_product_api.web.dto.UserCreateDto;
import com.marcosporto.demo_product_api.web.dto.UserResponseDto;
import com.marcosporto.demo_product_api.web.dto.UserUpdatePasswordDto;
import com.marcosporto.demo_product_api.web.dto.mapper.UserMapper;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recurso para cadastro, edição, exclusão e leitura de usuários.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

        private final UserService userService;

        @Operation(summary = "Criar um novo usuário.", description = "Recurso para criar um novo usuário.", responses = {
                        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateDto.class))),
                        @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })

        @PostMapping
        public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
                User user = userService.create(UserMapper.toUser(userCreateDto));
                return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
        }

        @Operation(summary = "Recuperar um usuário pelo o id.", security = @SecurityRequirement(name = "security"), description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENTE", responses = {
                        @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                        @ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        })
        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
        public ResponseEntity<UserResponseDto> searchByIdUser(@PathVariable Long id) {
                User user = userService.searchByIdUser(id);
                return ResponseEntity.ok(UserMapper.toDto(user));
        }

        @Operation(summary = "Atualizar senha.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENTE", security = @SecurityRequirement(name = "security"), responses = {
                        @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Senha não confere.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                        @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        })
        @PatchMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'MANAGER', 'SELLER') AND (#id == authentication.principal.id)")
        public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                        @Valid @RequestBody UserUpdatePasswordDto userUpdatePasswordDto) {
                userService.updatePassword(id, userUpdatePasswordDto.getCurrentPassword(),
                                userUpdatePasswordDto.getNewPassword(), userUpdatePasswordDto.getConfirmPassword());
                return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Listar todos os usuarios.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", security = @SecurityRequirement(name = "security"), responses = {
                        @ApiResponse(responseCode = "200", description = "Listar todos os usuario cadastrados.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
                        @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        })
        @GetMapping
        public ResponseEntity<List<UserResponseDto>> getAllUsers() {
                List<User> users = userService.getAllUsers();

                return ResponseEntity.ok(UserMapper.toListDto(users));

        }

}
