package com.marcosporto.demo_product_api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.marcosporto.demo_product_api.entity.Product;
import com.marcosporto.demo_product_api.web.dto.ProductCreateDto;
import com.marcosporto.demo_product_api.web.dto.ProductResponseDto;
import com.marcosporto.demo_product_api.web.dto.ProductUpdateDto;

public class ProductMapper {

    public static Product toProduct(ProductCreateDto productCreateDto) {
        return new ModelMapper().map(productCreateDto, Product.class);
    }

    public static ProductResponseDto toDto(Product product) {
        return new ModelMapper().map(product, ProductResponseDto.class);
    }

    public static List<ProductResponseDto> toListDto(List<Product> products) {
        return products.stream().map(product -> toDto(product)).collect(Collectors.toList());
    }

    public static Product toUpdateProduct(ProductUpdateDto productUpdateDto) {
        return new ModelMapper().map(productUpdateDto, Product.class);
    }

}
