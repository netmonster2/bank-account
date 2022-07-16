package org.kata.bankaccount.api.controller;

import org.kata.bankaccount.api.controller.dto.HistoryDto;
import org.kata.bankaccount.api.util.DtoConverter;
import org.kata.bankaccount.domain.model.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.kata.bankaccount.api.controller.HistoryController.ACCOUNT_HISTORY_BASE_ROUTE;

@RestController
@RequestMapping(ACCOUNT_HISTORY_BASE_ROUTE)
public class HistoryController extends BaseController {

    public static final String ACCOUNT_HISTORY_BASE_ROUTE = "/history";

    @io.swagger.v3.oas.annotations.Operation(summary = "Get the history of the account operations")
    @GetMapping("")
    public HistoryDto getAccountHistory() {
        logger.info("Requesting the account operations history");
        List<Operation> operations = bankAccServicePort.getBankAccount().getHistory().getOperationList();
        int currentBalance = bankAccServicePort.getBankAccount().getBalance();

        return DtoConverter.fromOperationsList(operations, currentBalance);
    }

}
