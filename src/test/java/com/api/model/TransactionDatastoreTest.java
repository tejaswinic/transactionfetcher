package com.api.model;

import com.api.data.Transaction;
import com.api.service.Main;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

/**
 * Created by tchintalapudi on 06/05/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TransactionDatastoreTest {

    @Autowired
    private TransactionDatastore transactionDatastore;
    private Transaction transaction;

    @Before
    public void setUp(){
        transactionDatastore.saveTransaction(new Transaction(5000.0, Instant.now().getEpochSecond()));
    }

    @Test
    public void testGetTransactions(){
        Assert.assertTrue(!transactionDatastore.getTransactions().isEmpty());
        Assert.assertEquals(transactionDatastore.getTransactions().size(),1);
    }

    @After
    public void clear() {
        transactionDatastore.deleteTransactions();
    }
}
