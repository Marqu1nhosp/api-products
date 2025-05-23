package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCreateDto {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, message = "O nome do produto deve ter entre no mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private Double price;

    @NotNull(message = "A categoria é obrigatória")
    private Long categoryId;
}
