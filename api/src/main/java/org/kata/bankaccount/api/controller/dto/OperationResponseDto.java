package org.kata.bankaccount.api.controller.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * This DTO class represents the data format of an account operation response
 */
@Getter
@Builder
public class OperationResponseDto {
    private int amount;
    private int balance;
    private String type;
    private String date;
}
