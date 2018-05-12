package dev.sanket.transactionstatistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.sanket.transactionstatistics.service.TransactionService;
import dev.sanket.transactionstatistics.service.TransactionServiceImpl;
import dev.sanket.transactionstatistics.util.TransactionRepository;

@Configuration
public class AppConfiguration {

    @Bean
    public TransactionRepository transactionRepository() {
        return new TransactionRepository();
    }
    
    @Bean
    public TransactionService transactionService() {
        return new TransactionServiceImpl();
    }
}
