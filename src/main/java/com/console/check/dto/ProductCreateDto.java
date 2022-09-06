package com.console.check.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateDto {

    String qua;

    String name;

    String cost;

    String promo;
}
