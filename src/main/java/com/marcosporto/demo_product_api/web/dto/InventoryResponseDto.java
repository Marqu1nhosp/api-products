package com.marcosporto.demo_product_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryResponseDto {
    private Long id;
    private Integer quantity;
    private String nameProduct;
}
