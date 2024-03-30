package com.jaeho.toilet.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaeho.toilet.controller.request.DocumentDto;
import com.jaeho.toilet.controller.request.MetaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapApiResponseDto {
    @JsonProperty("meta")
    private MetaDto metaDto;

    @JsonProperty("documents")
    private List<DocumentDto> documentList;
}
