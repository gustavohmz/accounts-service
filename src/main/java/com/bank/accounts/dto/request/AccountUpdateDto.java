package com.bank.accounts.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountUpdateDto {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Boolean status;
}
