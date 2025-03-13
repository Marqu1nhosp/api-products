package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdatePasswordDto {
    @NotBlank(message = "A senha atual não pode estar em branco.")
    private String currentPassword;

    @NotBlank(message = "A nova senha não pode estar em branco.")
    @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres.")
    private String newPassword;

    @NotBlank(message = "A confirmação da senha não pode estar em branco.")
    private String confirmPassword;
}
