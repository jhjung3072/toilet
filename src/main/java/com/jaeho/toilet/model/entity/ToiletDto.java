package com.jaeho.toilet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToiletDto {

    private Long id;
    private String toiletName;
    private String toiletAddress;
    private double latitude;
    private double longitude;
}