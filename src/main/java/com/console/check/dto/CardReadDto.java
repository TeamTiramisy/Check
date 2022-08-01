package com.console.check.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CardReadDto {

    Integer id;

    String bonus;
}
