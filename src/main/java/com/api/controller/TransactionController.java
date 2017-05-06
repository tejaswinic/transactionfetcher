package com.api.controller;

import com.api.model.TransactionDatastore;
import com.api.data.Statistics;
import com.api.data.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TransactionController {

    @Autowired
    private TransactionDatastore transactionDatastore;

    /**
     * POST request to save transactions to Store
     * @param transaction
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.POST,
        consumes = "application/json", produces = "application/json")
    public void postTransactions(@RequestBody Transaction transaction) {
        if(transaction == null || transaction.isEmpty()){
            throw new IllegalArgumentException("Transaction must be valid and in the following format: {\"amount\": 50.0, \"timestamp\":1494027830} ");
        }
        transactionDatastore.saveTransaction(transaction);
    }

    /**
     * GET request to get transactions in the last 60 seconds. Queries the store to get all transactions
     * @return Statistics
     */
    @RequestMapping(value = "/statistics", method=RequestMethod.GET)
    public Statistics getStatistics() {
        long currentTime = Instant.now().getEpochSecond();

        List<Transaction> transactionList = transactionDatastore.getTransactions().subMap(currentTime - 60, currentTime)
                                                                .values().stream()
                                                                .flatMap(t -> t.stream())
                                                                .collect(Collectors.toList());

        DoubleSummaryStatistics stats = transactionList.parallelStream()
                                                            .collect(Collectors.summarizingDouble(Transaction::getAmount));

        return new Statistics(stats.getSum(),stats.getAverage(),stats.getMax(),stats.getMin(),stats.getCount());
    }


    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


}
