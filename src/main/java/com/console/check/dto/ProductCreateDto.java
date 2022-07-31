package com.console.check.dto;

import com.console.check.entity.Promo;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductCreateDto {

    String qua;

    String name;

    String cost;

    String promo;
}
