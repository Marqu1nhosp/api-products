package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserCreateDto {
    @NotBlank
    @NotBlank(message = "O usuário é obrigatório")
    @Size(min = 3, message = "O usuário deve ter entre no mínimo 3 caracteres")
    @Email(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$", message = "Formato do e-mail está invalido!")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
