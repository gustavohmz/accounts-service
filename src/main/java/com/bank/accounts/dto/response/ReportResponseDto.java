package com.bank.accounts.dto.response;

import com.bank.accounts.dto.request.AccountReportDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportResponseDto {
    private Long clientId;
    private List<AccountReportDto> accounts;
}
