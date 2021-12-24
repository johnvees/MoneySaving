package com.yvs.moneysaving;

public class HistoryData {

    String transactionId, transactionCategoryName, transactionDescription, transactionDate, transactionAmount;


    public HistoryData(String transactionId, String transactionCategoryName, String transactionDescription, String transactionDate, String transactionAmount) {
        this.transactionId = transactionId;
        this.transactionCategoryName = transactionCategoryName;
        this.transactionDescription = transactionDescription;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionCategoryName() {
        return transactionCategoryName;
    }

    public void setTransactionCategoryName(String transactionCategoryName) {
        this.transactionCategoryName = transactionCategoryName;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
