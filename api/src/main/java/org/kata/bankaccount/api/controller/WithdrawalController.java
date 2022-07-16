package org.kata.bankaccount.api.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.kata.bankaccount.api.controller.dto.OperationRequestDto;
import org.kata.bankaccount.api.controller.dto.OperationResponseDto;
import org.kata.bankaccount.api.util.DtoConverter;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.model.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.kata.bankaccount.api.controller.WithdrawalController.ACCOUNT_WITHDRAWAL_BASE_ROUTE;


@RestController
@RequestMapping(ACCOUNT_WITHDRAWAL_BASE_ROUTE)
public class WithdrawalController extends BaseController {

    public static final String ACCOUNT_WITHDRAWAL_BASE_ROUTE = "/withdrawal";

    @io.swagger.v3.oas.annotations.Operation(summary = "Make a withdrawal from the bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The withdrawal is well done",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OperationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Balance is insufficient for the withdrawal",
                    content = @Content)})
    @PostMapping("")
    public OperationResponseDto withdraw(@Valid @RequestBody OperationRequestDto operationRequest) {
        try {
            logger.info("Requesting withdrawal of {}", operationRequest.getAmount());
            Operation operationModel = bankAccServicePort.getBankAccount().withdraw(operationRequest.getAmount());
            return DtoConverter.fromOperationModel(operationModel);
        } catch (InsufficientBalanceException e) {
            logger.warn("{} -> Requested amount: {}",
                    e.getMessage(), operationRequest.getAmount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
