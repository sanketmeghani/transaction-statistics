package dev.sanket.transactionstatistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.sanket.transactionstatistics.service.StatisticsService;
import dev.sanket.transactionstatistics.service.StatisticsServiceImpl;
import dev.sanket.transactionstatistics.service.TransactionService;
import dev.sanket.transactionstatistics.service.TransactionServiceImpl;

@Configuration
public class AppConfiguration {

    @Bean
    public TransactionService transactionService() {
        return new TransactionServiceImpl();
    }
    
    @Bean
    public StatisticsService statisticsService() {
        return new StatisticsServiceImpl();
    }
}
