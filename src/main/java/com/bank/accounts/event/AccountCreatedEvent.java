package com.bank.accounts.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedEvent {
    private Long accountId;
    private Long clientId;
    private String accountType;
}
