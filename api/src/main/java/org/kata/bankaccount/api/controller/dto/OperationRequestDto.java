package org.kata.bankaccount.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This DTO class represents the data format of an account operation request
 */
@Getter
@Setter
public class OperationRequestDto {
    @NotNull
    @Min(0)
    private int amount;
}
