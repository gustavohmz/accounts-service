package com.bank.accounts.mapper;

import com.bank.accounts.dto.request.MovementRequestDto;
import com.bank.accounts.dto.response.MovementResponseDto;
import com.bank.accounts.model.Movement;
import com.bank.accounts.model.Account;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper {

    public Movement toEntity(MovementRequestDto dto) {
        if (dto == null) return null;

        Account account = new Account();
        account.setId(dto.getAccountId());

        return Movement.builder()
                .account(account)
                .type(dto.getType())
                .amount(dto.getAmount())
                .build();
    }

    public MovementResponseDto toDto(Movement movement) {
        if (movement == null) return null;

        return MovementResponseDto.builder()
                .id(movement.getId())
                .accountId(movement.getAccount().getId())
                .type(movement.getType())
                .amount(movement.getAmount())
                .balance(movement.getBalance())
                .date(movement.getDate())
                .build();
    }
}
