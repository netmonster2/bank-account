package org.kata.bankaccount.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * This DTO class represents the data format of an account operation request
 */
@Getter
@Setter
public class OperationRequestDto {
    @Min(1)
    private int amount;
}
