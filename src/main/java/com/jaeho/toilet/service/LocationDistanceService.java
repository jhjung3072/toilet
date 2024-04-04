package com.jaeho.toilet.service;

import com.jaeho.toilet.controller.request.DocumentDto;
import com.jaeho.toilet.model.entity.LocationDistance;
import com.jaeho.toilet.model.entity.ToiletDto;
import com.jaeho.toilet.repository.LocationDistanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationDistanceService {
    private static final double EARTH_RADIUS_KM = 6371.0;

    private static final double RADIUS_KM = 3;
    private final LocationDistanceRepository locationDistanceRepository;

    private final ToiletService toiletService;

    @Transactional
    public List<LocationDistance> saveAll(List<LocationDistance> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return locationDistanceRepository.saveAll(directionList);
    }


    public List<LocationDistance> buildLocationList(DocumentDto documentDto) {
        if (documentDto == null) {
            return new ArrayList<>();
        }

        return toiletService.searchToiletDtoList()
                .stream()
                .map(toiletInfo -> createLocationDistance(documentDto, toiletInfo))
                .filter(location -> location.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(LocationDistance::getDistance))
                .collect(Collectors.toList());
    }


    private LocationDistance createLocationDistance(DocumentDto userLocation, ToiletDto toiletInfo) {
        return LocationDistance.builder()
                .userAddress(userLocation.getAddressName())
                .userLatitude(userLocation.getLatitude())
                .userLongitude(userLocation.getLongitude())
                .toiletName(toiletInfo.getToiletName())
                .toiletAddress(toiletInfo.getToiletAddress())
                .toiletLatitude(toiletInfo.getLatitude())
                .toiletLongitude(toiletInfo.getLongitude())
                .distance(
                        calculateDistance(
                                userLocation.getLatitude(), userLocation.getLongitude(),
                                toiletInfo.getLatitude(), toiletInfo.getLongitude())
                )
                .build();
    }



    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
