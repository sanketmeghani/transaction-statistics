package dev.sanket.transactionstatistics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.sanket.transactionstatistics.model.Transaction;
import dev.sanket.transactionstatistics.service.TransactionService;

@RestController("/transactions")
public class TransactionsController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> submitTransaction(@RequestBody Transaction transaction) {

        try {

            logger.info("Processing transaction - {}", transaction);
            transactionService.add(transaction);

        } catch (Exception e) {

            logger.error("Exception processing transaction - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
