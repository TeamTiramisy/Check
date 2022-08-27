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

    public Product map(Product product ,ProductCreateDto productCreateDto) {

        if (productCreateDto.getQua() != null){
            product.setQua(Integer.valueOf(productCreateDto.getQua()));
        }
        if (productCreateDto.getName() != null){
            product.setName(productCreateDto.getName());
        }
        if (productCreateDto.getCost() != null){
            product.setCost(Double.valueOf(productCreateDto.getCost()));
        }
        if (productCreateDto.getPromo() != null){
            product.setPromo(Promo.valueOf(productCreateDto.getPromo()));
        }

        return product;
    }
}
