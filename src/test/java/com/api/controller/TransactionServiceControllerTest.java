package com.api.controller;

import com.api.model.TransactionDatastore;
import com.api.data.Transaction;
import com.api.service.Main;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tchintalapudi on 06/05/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TransactionServiceControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Autowired
    TransactionDatastore transactionDatastore;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }


    @Test
    public void validPostRequestTest() throws Exception {
        String jsonTask = String.format("{\"amount\": 5000, \"timestamp\":1494023632}");
        JSONObject object = new JSONObject(jsonTask);
        this.mockMvc.perform(post("/transactions")
                                 .accept(MediaType.APPLICATION_JSON_UTF8)
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(jsonTask))
                    .andExpect(status().isOk());
        Assert.assertTrue(!transactionDatastore.getTransactions().isEmpty());
    }

    @Test
    public void invalidPostRequestTest() throws Exception {
        String requestBody = String.format("{\"timestamp\":1494023632}");
        this.mockMvc.perform(post("/transactions")
                                 .accept(MediaType.APPLICATION_JSON_UTF8)
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(requestBody))
                                 .andExpect(status().isBadRequest());
    }

    @Test
    public void validGetRequestTest() throws Exception {
        transactionDatastore.saveTransaction(new Transaction(5000.0, Instant.now().getEpochSecond() - 10 ));

        MvcResult result = mockMvc.perform(get("/statistics"))
                                  .andExpect(status().isOk())
                                  .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assert.assertEquals("{\"sum\":5000.0,\"avg\":5000.0,\"max\":5000.0,\"min\":5000.0,\"count\":1}",
                            result.getResponse().getContentAsString());
    }


    @After
    public void clear() {
        transactionDatastore.deleteTransactions();
    }
}