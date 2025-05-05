package com.bank.accounts.dto.response;

import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String clientId;
    private boolean status;
    private PersonDto person;
}
