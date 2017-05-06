package com.api.model;

import com.api.data.Transaction;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by tchintalapudi on 05/05/17.
 */
@Component
public class TransactionDatastore {

    private NavigableMap<Long,List<Transaction>> transactionMap;

    public TransactionDatastore(){
        this.transactionMap = new ConcurrentSkipListMap<>();
    }

    public NavigableMap<Long, List<Transaction>> getTransactions() {
        return transactionMap;
    }

    public void saveTransaction(Transaction transaction) {
        List<Transaction> transactionList = transactionMap.get(transaction.getTimestamp());
        if(transactionList == null)
            transactionList = new ArrayList<>();
        transactionList.add(transaction);
        this.transactionMap.put(transaction.getTimestamp(), transactionList);
    }

    //@Scheduled(fixedRate = 3600000)
    public void deleteTransactions() {
        transactionMap.clear();
    }
}
