package com.bank.accounts.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MovementRequestDto {
    private Long accountId;
    private String type;
    private BigDecimal amount;
}
