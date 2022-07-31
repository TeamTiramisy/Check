package com.console.check.mapper;

import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {

    private static final ProductCreateMapper INSTANCE = new ProductCreateMapper();

    @Override
    public Product map(ProductCreateDto object) {
        return Product.builder()
                .qua(Integer.valueOf(object.getQua()))
                .name(object.getName())
                .cost(Double.valueOf(object.getCost()))
                .promo(Promo.valueOf(object.getPromo()))
                .build();
    }

    public static ProductCreateMapper getInstance() {
        return INSTANCE;
    }
}
