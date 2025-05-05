package com.bank.accounts.controller;

import com.bank.accounts.dto.request.MovementRequestDto;
import com.bank.accounts.dto.response.MovementResponseDto;
import com.bank.accounts.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PostMapping
    public ResponseEntity<MovementResponseDto> createMovement(@Valid @RequestBody MovementRequestDto dto) {
        return new ResponseEntity<>(movementService.createMovement(dto), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<MovementResponseDto>> getMovementsByAccountAndDate(
            @PathVariable Long accountId,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        if (from == null) {
            from = LocalDate.of(1970, 1, 1);
        }
        if (to == null) {
            to = LocalDate.now();
        }

        return ResponseEntity.ok(
                movementService.getMovementsByAccountAndDate(accountId, from, to)
        );
    }

}
