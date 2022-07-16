package org.kata.bankaccount.api.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * This DTO class represents the data format of the operations' history when returned by REST API
 */
@Builder
@Getter
public class HistoryDto {
    @Singular
    private List<OperationResponseDto> operations;

    private int currentBalance;

}
