package com.jaeho.toilet.service;


import com.jaeho.toilet.controller.response.MapApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapSearchService {

    private final MapUriService mapUriService;

    private final WebClient.Builder webClientBuilder;
    private final RestTemplate restTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public MapApiResponseDto requestAddressSearch(String address) {
        if(ObjectUtils.isEmpty(address)) return null;
        URI uri = mapUriService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, MapApiResponseDto.class).getBody();
    }

}
