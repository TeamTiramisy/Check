package com.console.check.dto;

import com.console.check.entity.Promo;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductReadDto {

    Integer id;

    Integer qua;

    String name;

    Double cost;

    Promo promo;
}
