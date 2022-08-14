package com.console.check.mapper;

import com.console.check.dto.ProductCreateDto;

import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import org.springframework.stereotype.Component;


@Component
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {

    @Override
    public Product map(ProductCreateDto object) {
        return Product.builder()
                .qua(Integer.valueOf(object.getQua()))
                .name(object.getName())
                .cost(Double.valueOf(object.getCost()))
                .promo(Promo.valueOf(object.getPromo()))
                .build();
    }
}
