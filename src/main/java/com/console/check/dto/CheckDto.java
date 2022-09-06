package com.console.check.dto;

import lombok.*;

@Value
@Builder
public class CheckDto {

    Integer[] id;

    Integer[] qua;

    Integer card;
}
