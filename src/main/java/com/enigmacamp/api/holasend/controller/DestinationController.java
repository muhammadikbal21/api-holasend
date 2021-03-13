package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DestinationController {

    @Autowired
    DestinationRepository repository;


}
