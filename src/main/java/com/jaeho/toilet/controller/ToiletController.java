package com.jaeho.toilet.controller;

import com.jaeho.toilet.model.entity.Toilet;
import com.jaeho.toilet.service.ToiletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToiletController {

    private final ToiletService toiletService;


    @GetMapping("/toilets")
    public List<Toilet> findAllToilet(){
        return toiletService.findAll();
    }

}
