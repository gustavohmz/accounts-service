package com.bank.accounts.mapper;

import com.bank.accounts.dto.request.AccountRequestDto;
import com.bank.accounts.dto.response.AccountResponseDto;
import com.bank.accounts.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(AccountRequestDto dto) {
        if (dto == null) return null;

        return Account.builder()
                .accountNumber(dto.getAccountNumber())
                .type(dto.getType())
                .balance(dto.getInitialBalance())
                .status(dto.getStatus())
                .clientId(dto.getClientId())
                .build();
    }

    public AccountResponseDto toResponse(Account account) {
        if (account == null) return null;

        return AccountResponseDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .type(account.getType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .clientId(account.getClientId())
                .build();
    }
}
