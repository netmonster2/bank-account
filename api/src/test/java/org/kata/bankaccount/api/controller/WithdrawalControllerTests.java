package org.kata.bankaccount.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import java.util.Collections;

import static org.kata.bankaccount.api.controller.WithdrawalController.ACCOUNT_WITHDRAWAL_BASE_ROUTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Import(ApiTestConfiguration.class)
public class WithdrawalControllerTests extends BaseControllerTests {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("When I make a withdrawal with sufficient, the operation details should be returned")
    @Test
    public void withdrawalWithSufficientBalanceRequestReturnOperation() throws Exception {
        int depositAmount = 250;
        int withdrawalAmount = 200;

        Operation depositOperation = Operation.builder()
                .amount(depositAmount)
                .type(Operation.Type.DEPOSIT)
                .build();

        Operation withdrawalOperation = Operation.builder()
                .amount(withdrawalAmount)
                .type(Operation.Type.WITHDRAW)
                .build();

        OperationRequestDto operationRequestDto = new OperationRequestDto();
        operationRequestDto.setAmount(withdrawalAmount);
        String requestJson = objectMapper.writeValueAsString(operationRequestDto);

        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(Collections.singletonList(depositOperation));
        given(this.bankAccPersistencePort.saveOperation(any(Operation.class)))
                //Return the same argument to get the updated balance value
                .willAnswer(invocation -> invocation.getArguments()[0]);

        String expectedDateValue = DateTimeUtil.formatDateFr(withdrawalOperation.getDate());
        int expectedBalance = depositAmount - withdrawalAmount;

        this.mockMvc.perform(post(ACCOUNT_WITHDRAWAL_BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(-1 * withdrawalAmount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(expectedBalance))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(DtoConverter.WITHDRAWAL_OP_LABEL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(expectedDateValue));
    }

    @DisplayName("When I make a withdrawal with insufficient, the error message should be returned")
    @Test
    public void withdrawalWithInsufficientBalanceRequestReturnError() throws Exception {
        int depositAmount = 250;
        int withdrawalAmount = 300;

        Operation depositOperation = Operation.builder()
                .amount(depositAmount)
                .type(Operation.Type.DEPOSIT)
                .build();

        OperationRequestDto operationRequestDto = new OperationRequestDto();
        operationRequestDto.setAmount(withdrawalAmount);
        String requestJson = objectMapper.writeValueAsString(operationRequestDto);

        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(Collections.singletonList(depositOperation));

        this.mockMvc.perform(post(ACCOUNT_WITHDRAWAL_BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("Account balance is insufficient")));
    }

    @DisplayName("When I make a withdrawal with 0 amount, the error message should be returned")
    @Test
    public void withdrawalWith0AMountRequestReturnError() throws Exception {
        int withdrawalAmount = 0;

        assertErrorReturned(withdrawalAmount);
    }

    @DisplayName("When I make a withdrawal with a negative amount, the error message should be returned")
    @Test
    public void withdrawalWithNegativeAmountRequestReturnError() throws Exception {
        int withdrawalAmount = -100;

        assertErrorReturned(withdrawalAmount);
    }

    private void assertErrorReturned(int withdrawalAmount) throws Exception {
        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(new ArrayList<>());

        OperationRequestDto operationRequestDto = new OperationRequestDto();
        operationRequestDto.setAmount(withdrawalAmount);
        String requestJson = objectMapper.writeValueAsString(operationRequestDto);

        this.mockMvc.perform(post(ACCOUNT_WITHDRAWAL_BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("The amount")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("is invalid")));
    }
}
