package com.bank.accounts.service.impl;

import com.bank.accounts.dto.request.MovementRequestDto;
import com.bank.accounts.dto.response.MovementResponseDto;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.MovementMapper;
import com.bank.accounts.model.Account;
import com.bank.accounts.model.Movement;
import com.bank.accounts.repository.AccountRepository;
import com.bank.accounts.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementMapper movementMapper;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void createMovement_ShouldReturnMovementResponseDto_WhenAccountExists() {
        MovementRequestDto dto = new MovementRequestDto();
        dto.setAccountId(1L);
        dto.setAmount(BigDecimal.valueOf(100));
        dto.setType("Deposit");

        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));

        Movement movement = Movement.builder()
                .account(account)
                .amount(dto.getAmount())
                .type(dto.getType())
                .date(LocalDate.now())
                .build();

        Movement saved = Movement.builder()
                .id(1L)
                .account(account)
                .amount(dto.getAmount())
                .type(dto.getType())
                .date(LocalDate.now())
                .build();

        MovementResponseDto responseDto = MovementResponseDto.builder()
                .id(1L)
                .accountId(1L)
                .amount(dto.getAmount())
                .type(dto.getType())
                .date(LocalDate.now())
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(movementMapper.toEntity(dto)).thenReturn(movement);
        when(movementRepository.save(any(Movement.class))).thenReturn(saved);
        when(movementMapper.toDto(saved)).thenReturn(responseDto);

        MovementResponseDto result = movementService.createMovement(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(movementRepository, times(1)).save(any(Movement.class));
    }

    @Test
    void createMovement_ShouldThrowException_WhenAccountNotFound() {
        MovementRequestDto dto = new MovementRequestDto();
        dto.setAccountId(100L);

        when(accountRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movementService.createMovement(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Account not found");

        verify(movementRepository, never()).save(any());
    }

    @Test
    void getMovementsByAccountAndDate_ShouldReturnList() {
        Account account = new Account();
        account.setId(1L);

        Movement movement = new Movement();
        movement.setId(1L);
        movement.setAccount(account);
        movement.setAmount(BigDecimal.valueOf(100));
        movement.setType("Withdrawal");
        movement.setDate(LocalDate.now());

        MovementResponseDto dto = MovementResponseDto.builder()
                .id(1L)
                .accountId(1L)
                .amount(movement.getAmount())
                .type(movement.getType())
                .date(movement.getDate())
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(movementRepository.findByAccountIdAndDateBetween(eq(1L), any(), any())).thenReturn(Collections.singletonList(movement));
        when(movementMapper.toDto(movement)).thenReturn(dto);

        var result = movementService.getMovementsByAccountAndDate(1L, LocalDate.now().minusDays(1), LocalDate.now());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }
}
