package org.kata.bankaccount.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.api.ApiTestConfiguration;
import org.kata.bankaccount.api.controller.dto.OperationRequestDto;
import org.kata.bankaccount.api.util.DateTimeUtil;
import org.kata.bankaccount.api.util.DtoConverter;
import org.kata.bankaccount.domain.model.Operation;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.kata.bankaccount.api.controller.DepositController.ACCOUNT_DEPOSIT_BASE_ROUTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Import(ApiTestConfiguration.class)
public class DepositControllerTests extends BaseControllerTests {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("When I make a deposit, the operation details should be returned")
    @Test
    public void depositRequestReturnOperation() throws Exception {
        int depositAmount = 0;

        Operation depositOperation = Operation.builder()
                .amount(depositAmount)
                .type(Operation.Type.DEPOSIT)
                .build();

        OperationRequestDto operationRequestDto = new OperationRequestDto();
        operationRequestDto.setAmount(depositAmount);
        String requestJson = objectMapper.writeValueAsString(operationRequestDto);

        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(new ArrayList<>());
        given(this.bankAccPersistencePort.saveOperation(any(Operation.class)))
                //Return the same argument to get the updated balance value
                .willAnswer(invocation -> invocation.getArguments()[0]);

        String expectedDateValue = DateTimeUtil.formatDateFr(depositOperation.getDate());

        this.mockMvc.perform(post(ACCOUNT_DEPOSIT_BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(depositAmount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(depositAmount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(DtoConverter.DEPOSIT_OP_LABEL));
    }
}
