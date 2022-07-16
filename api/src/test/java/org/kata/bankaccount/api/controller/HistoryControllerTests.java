package org.kata.bankaccount.api.controller;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.api.ApiTestConfiguration;
import org.kata.bankaccount.domain.model.Operation;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;

import static org.kata.bankaccount.api.controller.HistoryController.ACCOUNT_HISTORY_BASE_ROUTE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@Import(ApiTestConfiguration.class)
public class HistoryControllerTests extends BaseControllerTests {

    @DisplayName("When I call the get history request before any operation, " +
            "the operations list should be empty and balance is 0")
    @Test
    public void getHistoryBeforeAnyOpReturnEmptyList() throws Exception {
        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(new ArrayList<>());

        this.mockMvc.perform(get(ACCOUNT_HISTORY_BASE_ROUTE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operations").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operations", IsEmptyCollection.empty()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentBalance").value(0));
    }

    @DisplayName("When I call the get history request, the operations list should be returned")
    @Test
    public void getHistoryShouldReturnOperationsList() throws Exception {
        Operation operation = Operation.builder()
                .type(Operation.Type.DEPOSIT)
                .balance(35)
                .amount(35)
                .build();

        given(this.bankAccPersistencePort.loadOperations())
                .willReturn(Collections.singletonList(operation));

        this.mockMvc.perform(get(ACCOUNT_HISTORY_BASE_ROUTE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operations.[0].balance").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operations.[0].amount").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentBalance").value(35));
    }

}
