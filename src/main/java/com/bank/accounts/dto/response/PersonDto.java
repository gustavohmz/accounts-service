package com.bank.accounts.dto.response;

import lombok.Data;

@Data
public class PersonDto {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
