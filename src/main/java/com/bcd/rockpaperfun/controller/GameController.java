package com.bcd.rockpaperfun.controller;

import com.bcd.rockpaperfun.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/calculate-result")
    public ResponseEntity<?> calculateResult() {
        gameService.calculateAndApplyResults();
        return ResponseEntity.ok("Result calculated and applied");
    }
}
