package com.bank.accounts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponseDto {
    private Long id;
    private LocalDate date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
    private Long accountId;
}
