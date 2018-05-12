package dev.sanket.transactionstatistics.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.sanket.transactionstatistics.model.Statistics;

@RestController("/statistics")
public class StatisticsController {

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Statistics> getStatistics() {
        return ResponseEntity.ok(new Statistics());
    }
}
