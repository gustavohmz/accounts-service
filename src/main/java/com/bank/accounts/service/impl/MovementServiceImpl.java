package com.bank.accounts.service.impl;

import com.bank.accounts.dto.request.MovementRequestDto;
import com.bank.accounts.dto.response.MovementResponseDto;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.MovementMapper;
import com.bank.accounts.model.Account;
import com.bank.accounts.model.Movement;
import com.bank.accounts.repository.AccountRepository;
import com.bank.accounts.repository.MovementRepository;
import com.bank.accounts.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MovementMapper movementMapper;

    @Override
    @Transactional
    public MovementResponseDto createMovement(MovementRequestDto requestDto) {
        Account account = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + requestDto.getAccountId()));

        BigDecimal currentBalance = account.getBalance() != null
                ? account.getBalance()
                : account.getInitialBalance();

        BigDecimal newBalance;

        if ("Withdrawal".equalsIgnoreCase(requestDto.getType())) {
            if (currentBalance.compareTo(requestDto.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            newBalance = currentBalance.subtract(requestDto.getAmount());
        } else {
            newBalance = currentBalance.add(requestDto.getAmount());
        }

        account.setBalance(newBalance);
        accountRepository.save(account);


        Movement movement = movementMapper.toEntity(requestDto);
        movement.setAccount(account);
        movement.setDate(LocalDate.now());
        movement.setBalance(newBalance);

        Movement saved = movementRepository.save(movement);
        return movementMapper.toDto(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public List<MovementResponseDto> getMovementsByAccountAndDate(Long accountId, LocalDate from, LocalDate to) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        List<Movement> movements = movementRepository.findByAccountIdAndDateBetween(accountId, from, to);

        return movements.stream()
                .map(movementMapper::toDto)
                .collect(Collectors.toList());
    }
}
