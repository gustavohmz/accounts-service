package com.bank.accounts.client;

import com.bank.accounts.dto.response.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-service", url = "${USER_SERVICE_URL}")
public interface UserClient {
    @GetMapping("/api/clients/{id}")
    ClientDto getClientById(@PathVariable("id") Long id);
}

