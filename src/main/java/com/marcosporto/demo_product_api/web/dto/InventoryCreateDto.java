package com.marcosporto.demo_product_api.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryCreateDto {

    @NotNull(message = "A quantidade deve ser informado")
    private Integer quantity;

    @NotNull(message = "O produto tem que ser informado")
    private long productId;
}
