package dev.sanket.transactionstatistics.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dev.sanket.transactionstatistics.service.StatisticsService;
import dev.sanket.transactionstatistics.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldReturnStatisticsWith200() throws Exception {

        Map<String, Object> snapshot = new HashMap<>();

        snapshot.put("min", Double.valueOf(0.0));
        snapshot.put("max", Double.valueOf(0.0));
        snapshot.put("avg", Double.valueOf(0.0));
        snapshot.put("sum", Double.valueOf(0.0));
        snapshot.put("count", Integer.valueOf(0));

        given(statisticsService.getStatistics()).willReturn(snapshot);

        mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("min", is(Double.valueOf(0.0)))).andExpect(jsonPath("max", is(Double.valueOf(0.0))))
                .andExpect(jsonPath("avg", is(Double.valueOf(0.0)))).andExpect(jsonPath("sum", is(Double.valueOf(0.0))))
                .andExpect(jsonPath("count", is(Integer.valueOf(0))));
    }
}
