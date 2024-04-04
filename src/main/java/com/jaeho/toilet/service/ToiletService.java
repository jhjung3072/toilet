package com.jaeho.toilet.service;

import com.jaeho.toilet.model.entity.Toilet;
import com.jaeho.toilet.model.entity.ToiletDto;
import com.jaeho.toilet.repository.ToiletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToiletService {
    private final ToiletRepository toiletRepository;

    @Transactional(readOnly = true)
    public List<Toilet> findAll() {
        return toiletRepository.findAll();
    }

    public List<ToiletDto> searchToiletDtoList() {

        return toiletRepository.findByIsAvailable(true)
                .stream()
                .map(this::convertToToiletDto)
                .collect(Collectors.toList());
    }

    private ToiletDto convertToToiletDto(Toilet toilet) {

        return ToiletDto.builder()
                .id(toilet.getToiletId())
                .toiletName(toilet.getName())
                .toiletAddress(toilet.getAddress())
                .latitude(toilet.getLatitude())
                .longitude(toilet.getLongitude())
                .build();
    }
}
