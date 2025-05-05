package com.bank.accounts.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountReportDto {
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private List<MovementReportDto> movements;
}
