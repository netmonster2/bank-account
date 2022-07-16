package org.kata.bankaccount.api.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.kata.bankaccount.domain.model.Operation;

import java.util.List;

/**
 * This DTO class represents the data format of the operations' history when returned by REST API
 */
@Builder
@Getter
public class HistoryDto {
    @Singular
    private List<Operation> operations;

    private int currentBalance;

}
