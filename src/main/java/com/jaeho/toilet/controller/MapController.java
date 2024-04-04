package com.jaeho.toilet.controller;

import com.jaeho.toilet.controller.request.MapRequest;
import com.jaeho.toilet.controller.response.Response;
import com.jaeho.toilet.controller.response.UserJoinResponse;
import com.jaeho.toilet.model.entity.ToiletLocationDto;
import com.jaeho.toilet.service.MapSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MapController {

    private final MapSearchService mapSearchService;

//    @PostMapping("/search/address")
//    public ResponseEntity<MapApiResponseDto> searchAddress(@RequestBody MapRequest request) {
//        return new ResponseEntity<MapApiResponseDto>(mapSearchService.requestAddressSearch(request.getAddress()), HttpStatus.OK);
//    }

    @PostMapping("/find/toilet")
    public Response<List<ToiletLocationDto>> findToilet(@RequestBody MapRequest request) {
        List<ToiletLocationDto> toiletLocationDtos = mapSearchService.findNearbyPublicToilets(request.getAddress());
        return Response.success(toiletLocationDtos);
    }

}
