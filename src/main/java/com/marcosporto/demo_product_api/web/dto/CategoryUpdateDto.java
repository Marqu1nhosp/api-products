package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryUpdateDto {
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, message = "O nome deve ter entre no mínimo 3 caracteres")
    private String name;
}
