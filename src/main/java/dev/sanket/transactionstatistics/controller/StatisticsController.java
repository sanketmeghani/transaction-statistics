package dev.sanket.transactionstatistics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.sanket.transactionstatistics.model.Statistics;

@RestController("/statistics")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Statistics> getStatistics() {

        Statistics statistics = new Statistics();
        logger.info("Returning statistics - {}", statistics);
        return ResponseEntity.ok(statistics);
    }
}
