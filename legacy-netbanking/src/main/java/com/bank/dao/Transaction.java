package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Simple value object for Transaction.
 */
public class Transaction {

    private int id;
    private int fromAccountId;
    private int toAccountId;
    private BigDecimal amount;
    private String description;
    private Timestamp timestamp;
    private String fromAccountNumber;
    private String toAccountNumber;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(int fromAccountId) { this.fromAccountId = fromAccountId; }
    public int getToAccountId() { return toAccountId; }
    public void setToAccountId(int toAccountId) { this.toAccountId = toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public String getFromAccountNumber() { return fromAccountNumber; }
    public void setFromAccountNumber(String fromAccountNumber) { this.fromAccountNumber = fromAccountNumber; }
    public String getToAccountNumber() { return toAccountNumber; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }
}
