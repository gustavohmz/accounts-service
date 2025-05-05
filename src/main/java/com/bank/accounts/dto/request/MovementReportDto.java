package com.bank.accounts.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MovementReportDto {
    private LocalDate date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
}
