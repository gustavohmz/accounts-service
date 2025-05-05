package com.bank.accounts.service;


import com.bank.accounts.dto.response.ReportResponseDto;

import java.time.LocalDate;

public interface ReportService {
    ReportResponseDto generateReport(Long clientId, LocalDate from, LocalDate to);
}
