package org.kata.bankaccount.api.util;

import org.kata.bankaccount.api.controller.dto.HistoryDto;
import org.kata.bankaccount.api.controller.dto.OperationResponseDto;
import org.kata.bankaccount.domain.model.Operation;

import java.util.List;

public class DtoConverter {

    public static final String DEPOSIT_OP_LABEL = "Deposit";
    public static final String WITHDRAWAL_OP_LABEL = "Withdrawal";

    /**
     * Converts an Operation model to an Operation Reponse DTO
     *
     * @param operationModel {@link Operation} model
     * @return An {@link OperationResponseDto} object
     */
    public static OperationResponseDto fromOperationModel(Operation operationModel) {
        return OperationResponseDto.builder()
                .amount(operationModel.getAmount())
                .balance(operationModel.getBalance())
                .date(DateTimeUtil.formatDateFr(operationModel.getDate()))
                .type(operationModel.getType() == Operation.Type.DEPOSIT ? DEPOSIT_OP_LABEL : WITHDRAWAL_OP_LABEL)
                .build();
    }

    /**
     * Converts a list of Operation model to a History DTO
     *
     * @param operationList  List of {@link Operation} model
     * @param currentBalance the current account balance
     * @return An {@link HistoryDto} object
     */
    public static HistoryDto fromOperationsList(List<Operation> operationList, int currentBalance) {
        HistoryDto.HistoryDtoBuilder historyDtoBuilder = HistoryDto.builder();
        historyDtoBuilder.currentBalance(currentBalance);
        operationList.forEach(historyDtoBuilder::operation);
        return historyDtoBuilder.build();
    }
}
