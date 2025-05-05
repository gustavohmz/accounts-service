package com.bank.accounts.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequestDto {
    private String accountNumber;
    private String type;
    private BigDecimal initialBalance;
    private Boolean status;
    private Long clientId;
}
