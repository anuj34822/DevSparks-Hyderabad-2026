package com.bank.forms;

import org.apache.struts.action.ActionForm;

public class AdminAccountForm extends ActionForm {
    private String userId;
    private String accountNumber;
    private String initialBalance;
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getInitialBalance() { return initialBalance; }
    public void setInitialBalance(String initialBalance) { this.initialBalance = initialBalance; }
}
