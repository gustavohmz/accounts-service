package com.bank.accounts.service;

import com.bank.accounts.dto.request.AccountRequestDto;
import com.bank.accounts.dto.request.AccountUpdateDto;
import com.bank.accounts.dto.response.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto requestDto);
    AccountResponseDto updateAccount(Long id, AccountUpdateDto updateDto);
    void deleteAccount(Long id);
    AccountResponseDto getAccountById(Long id);
    List<AccountResponseDto> getAllAccounts();
}
