package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryCreateDto {
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(min = 3, message = "O Categoria deve ter entre no mínimo 3 caracteres")
    private String name;

}
