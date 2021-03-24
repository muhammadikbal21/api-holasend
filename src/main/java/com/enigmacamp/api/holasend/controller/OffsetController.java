package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.models.ResponseMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offset")
public class OffsetController {

    private Double offset = 100.0;

    @GetMapping
    public ResponseMessage<Double> getOffset() {
        return ResponseMessage.success(offset);
    }

    @PutMapping("/{input}")
    public ResponseMessage<Double> editOffset(
            @PathVariable Double input
    ) {
        offset = input;
        return ResponseMessage.success(offset);
    }
}
