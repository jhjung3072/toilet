package com.jaeho.toilet.service;

import com.jaeho.toilet.model.entity.Toilet;
import com.jaeho.toilet.repository.ToiletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToiletService {
    private final ToiletRepository toiletRepository;

    @Transactional(readOnly = true)
    public List<Toilet> findAll() {
        return toiletRepository.findAll();
    }

}
