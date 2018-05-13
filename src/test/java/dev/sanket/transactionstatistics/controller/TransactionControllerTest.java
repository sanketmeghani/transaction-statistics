package dev.sanket.transactionstatistics.controller;

import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dev.sanket.transactionstatistics.exception.TransactionServiceException;
import dev.sanket.transactionstatistics.model.Transaction;
import dev.sanket.transactionstatistics.service.StatisticsService;
import dev.sanket.transactionstatistics.service.TransactionService;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@WebMvcTest
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldReturn204ForOlderTransactions() throws Exception {

        Transaction transaction = new Transaction();

        transaction.setAmount(12.5);
        transaction.setTimestamp(1478192204000L);

        willThrow(TransactionServiceException.class).given(transactionService).add(Matchers.any(Transaction.class));

        mvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON).content(transaction.toString()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void shouldReturn201ForAcceptedTransactions() throws Exception {

        Transaction transaction = new Transaction();

        transaction.setAmount(12.5);
        transaction.setTimestamp(System.currentTimeMillis());

        mvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON).content(transaction.toString()))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }
}
