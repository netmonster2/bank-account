package org.kata.bankaccount.api.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.kata.bankaccount.api.controller.dto.OperationRequestDto;
import org.kata.bankaccount.api.controller.dto.OperationResponseDto;
import org.kata.bankaccount.api.util.DtoConverter;
import org.kata.bankaccount.domain.model.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(DepositController.ACCOUNT_DEPOSIT_BASE_ROUTE)
public class DepositController extends BaseController {

    public static final String ACCOUNT_DEPOSIT_BASE_ROUTE = "/deposit";

    @io.swagger.v3.oas.annotations.Operation(summary = "Make a deposit in the bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The deposit is well done",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OperationResponseDto.class))})})
    @PostMapping("")
    public OperationResponseDto deposit(@Valid @RequestBody OperationRequestDto operationRequest) {
        logger.info("Requesting deposit of {}", operationRequest.getAmount());
        Operation operationModel = bankAccServicePort.getBankAccount().deposit(operationRequest.getAmount());
        return DtoConverter.fromOperationModel(operationModel);
    }

}
