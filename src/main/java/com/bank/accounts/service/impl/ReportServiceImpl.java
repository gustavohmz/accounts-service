package com.bank.accounts.service.impl;


import com.bank.accounts.dto.request.AccountReportDto;
import com.bank.accounts.dto.request.MovementReportDto;
import com.bank.accounts.dto.response.ReportResponseDto;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.model.Account;
import com.bank.accounts.model.Movement;
import com.bank.accounts.repository.AccountRepository;
import com.bank.accounts.repository.MovementRepository;
import com.bank.accounts.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Override
    public ReportResponseDto generateReport(Long clientId, LocalDate from, LocalDate to) {
        List<Account> accounts = accountRepository.findByClientId(clientId);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No accounts found for client ID: " + clientId);
        }

        List<AccountReportDto> accountReports = accounts.stream().map(account -> {
            List<Movement> movements = movementRepository
                    .findByAccountIdAndDateBetween(account.getId(), from, to);

            List<MovementReportDto> movementDtos = movements.stream()
                    .map(mov -> MovementReportDto.builder()
                            .date(mov.getDate())
                            .type(mov.getType())
                            .amount(mov.getAmount())
                            .balance(mov.getBalance())
                            .build())
                    .collect(Collectors.toList());

            return AccountReportDto.builder()
                    .accountNumber(account.getAccountNumber())
                    .type(account.getType())
                    .balance(account.getBalance())
                    .movements(movementDtos)
                    .build();

        }).collect(Collectors.toList());

        return ReportResponseDto.builder()
                .clientId(clientId)
                .accounts(accountReports)
                .build();
    }
}
