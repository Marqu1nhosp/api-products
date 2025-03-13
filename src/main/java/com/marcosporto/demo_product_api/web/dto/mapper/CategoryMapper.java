package com.marcosporto.demo_product_api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.marcosporto.demo_product_api.entity.Category;

import com.marcosporto.demo_product_api.web.dto.CategoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.CategoryResponseDto;
import com.marcosporto.demo_product_api.web.dto.CategoryUpdateDto;

public class CategoryMapper {

    public static Category toCategory(CategoryCreateDto categoryCreateDto) {
        return new ModelMapper().map(categoryCreateDto, Category.class);
    }

    public static CategoryResponseDto toDto(Category category) {
        return new ModelMapper().map(category, CategoryResponseDto.class);
    }

    public static List<CategoryResponseDto> toListDto(List<Category> categories) {
        return categories.stream().map(category -> toDto(category)).collect(Collectors.toList());
    }

    public static Category toUpdateCategory(CategoryUpdateDto categoryUpdateDto) {
        return new ModelMapper().map(categoryUpdateDto, Category.class);
    }
}
