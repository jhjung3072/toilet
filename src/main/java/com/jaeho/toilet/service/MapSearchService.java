package com.jaeho.toilet.service;


import com.jaeho.toilet.controller.request.DocumentDto;
import com.jaeho.toilet.controller.response.MapApiResponseDto;
import com.jaeho.toilet.model.entity.LocationDistance;
import com.jaeho.toilet.model.entity.ToiletLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapSearchService {
    private final MapUriService mapUriService;

    private final WebClient.Builder webClientBuilder;
    private final RestTemplate restTemplate;

    private final LocationDistanceService locationDistanceService;

    @Value(" ${kakao.rest.api.key}")
    private String kakaoRestApiKey;
    public MapApiResponseDto requestAddressSearch(String address) {
        if(ObjectUtils.isEmpty(address)) return null;
        URI uri = mapUriService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, MapApiResponseDto.class).getBody();
    }

    public List<ToiletLocationDto> findNearbyPublicToilets(String address) {

        MapApiResponseDto mapApiResponseDto = this.requestAddressSearch(address);

        if (Objects.isNull(mapApiResponseDto) || CollectionUtils.isEmpty(mapApiResponseDto.getDocumentList())) {
            return Collections.emptyList();
        }

        DocumentDto documentDto = mapApiResponseDto.getDocumentList().get(0);

        List<LocationDistance> directionList = locationDistanceService.buildLocationList(documentDto);

        return locationDistanceService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }
    private ToiletLocationDto convertToOutputDto(LocationDistance locationDistance) {

        return ToiletLocationDto.builder()
                .toiletName(locationDistance.getToiletName())
                .toiletAddress(locationDistance.getToiletAddress())
                .distance(String.format("%.2f km", locationDistance.getDistance()))
                .build();
    }
}
