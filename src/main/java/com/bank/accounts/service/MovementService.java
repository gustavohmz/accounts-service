package com.bank.accounts.service;

import com.bank.accounts.dto.request.MovementRequestDto;
import com.bank.accounts.dto.response.MovementResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface MovementService {
    MovementResponseDto createMovement(MovementRequestDto requestDto);
    List<MovementResponseDto> getMovementsByAccountAndDate(Long accountId, LocalDate from, LocalDate to);
}
