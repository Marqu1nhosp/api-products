package com.marcosporto.demo_product_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryResponseDto {
    private Long id;
    private String name;
}
