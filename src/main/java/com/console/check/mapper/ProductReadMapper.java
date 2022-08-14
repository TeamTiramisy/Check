package com.console.check.mapper;

import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;

import org.springframework.stereotype.Component;


@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .id(object.getId())
                .qua(object.getQua())
                .name(object.getName())
                .cost(object.getCost())
                .promo(object.getPromo())
                .build();
    }

    public ProductReadDto copy(ProductReadDto object, Integer qua) {
        return ProductReadDto.builder()
                .id(object.getId())
                .qua(qua)
                .name(object.getName())
                .cost(object.getCost())
                .promo(object.getPromo())
                .build();
    }
}
