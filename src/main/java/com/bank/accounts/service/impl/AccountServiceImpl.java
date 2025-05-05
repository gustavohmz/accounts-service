package com.bank.accounts.service.impl;

import com.bank.accounts.client.UserClient;
import com.bank.accounts.dto.request.AccountRequestDto;
import com.bank.accounts.dto.request.AccountUpdateDto;
import com.bank.accounts.dto.response.AccountResponseDto;
import com.bank.accounts.dto.response.ClientDto;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountMapper;
import com.bank.accounts.model.Account;
import com.bank.accounts.repository.AccountRepository;
import com.bank.accounts.service.AccountService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserClient userClient;

    @Override
    public AccountResponseDto createAccount(AccountRequestDto requestDto) {
        try {
            userClient.getClientById(requestDto.getClientId());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Client with ID " + requestDto.getClientId() + " does not exist");
        }

        Account account = accountMapper.toEntity(requestDto);
        account.setInitialBalance(requestDto.getInitialBalance());
        account.setBalance(requestDto.getInitialBalance());
        account.setStatus(true);

        try {
            return accountMapper.toResponse(accountRepository.save(account));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("An account with the same account number already exists");
        }
    }


    @Override
    public AccountResponseDto updateAccount(Long id, AccountUpdateDto updateDto) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));

        existing.setAccountNumber(updateDto.getAccountNumber());
        existing.setType(updateDto.getAccountType());
        existing.setInitialBalance(updateDto.getInitialBalance());
        existing.setStatus(updateDto.getStatus());

        return accountMapper.toResponse(accountRepository.save(existing));
    }

    @Override
    public void deleteAccount(Long id) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
        accountRepository.delete(existing);
    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }
}
