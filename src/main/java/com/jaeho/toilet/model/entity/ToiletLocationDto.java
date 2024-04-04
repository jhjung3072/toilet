package com.jaeho.toilet.model.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToiletLocationDto {

    private String toiletName;
    private String toiletAddress;
    private String distance;
}
