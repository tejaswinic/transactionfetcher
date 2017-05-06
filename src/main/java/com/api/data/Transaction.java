package com.api.data;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by tchintalapudi on 04/05/17.
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 4782612550980962226L;

    @NotEmpty(message = "Data for amount must be provided")
    private Double amount;

    @NotBlank(message = "Timestamp can't be blank")
    private Long timestamp;

    public Transaction(){

    }

    public Transaction(Double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isEmpty() {
        return (this.getAmount() == null || this.getTimestamp() == 0);
    }
}
