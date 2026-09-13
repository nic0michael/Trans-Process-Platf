package com.nm.tranproc.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private LocalDateTime timestamp;
    private Long transactionIndex;
    private String transactionGuid;
    private String requestId;
    private String externalSystemId;
    private String transactionType;
    private String currency;
    private BigDecimal amount;
    private String reference;
    private String transactionDescription;
    private Long companyId;
    private String status;

    public TransactionDto(){}

    public TransactionDto(LocalDateTime timestamp, Long transactionIndex,
                          String transactionGuid, String requestId, String externalSystemId, String transactionType, String currency, BigDecimal amount, String reference, String transactionDescription, Long companyId, String status) {
      this.timestamp = timestamp;
      this.transactionIndex = transactionIndex;
      this.transactionGuid = transactionGuid;
      this.requestId = requestId;
      this.externalSystemId = externalSystemId;
      this.transactionType = transactionType;
      this.currency = currency;
      this.amount = amount;
      this.reference = reference;
      this.transactionDescription = transactionDescription;
      this.companyId = companyId;
      this.status = status;
    }

    @Override
    public String toString() {
      return "TransactionDto{" +
          "timestamp=" + timestamp +
          ", transactionIndex=" + transactionIndex +
          ", transactionGuid=" + transactionGuid +
          ", requestId=" + requestId +
          ", externalSystemId='" + externalSystemId + '\'' +
          ", transactionType='" + transactionType + '\'' +
          ", currency='" + currency + '\'' +
          ", amount=" + amount +
          ", reference='" + reference + '\'' +
          ", transactionDescription='" + transactionDescription + '\'' +
          ", companyId=" + companyId +
          ", status='" + status + '\'' +
          '}';
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }

    public Long getTransactionIndex() {
      return transactionIndex;
    }

    public void setTransactionIndex(Long transactionIndex) {
      this.transactionIndex = transactionIndex;
    }

    public String getTransactionGuid() {
      return transactionGuid;
    }

    public void setTransactionGuid(String transactionGuid) {
      this.transactionGuid = transactionGuid;
    }

    public String getRequestId() {
      return requestId;
    }

    public void setRequestId(String requestId) {
      this.requestId = requestId;
    }

    public String getExternalSystemId() {
      return externalSystemId;
    }

    public void setExternalSystemId(String externalSystemId) {
      this.externalSystemId = externalSystemId;
    }

    public String getTransactionType() {
      return transactionType;
    }

    public void setTransactionType(String transactionType) {
      this.transactionType = transactionType;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public BigDecimal getAmount() {
      return amount;
    }

    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }

    public String getReference() {
      return reference;
    }

    public void setReference(String reference) {
      this.reference = reference;
    }

    public String getTransactionDescription() {
      return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
      this.transactionDescription = transactionDescription;
    }

    public Long getCompanyId() {
      return companyId;
    }

    public void setCompanyId(Long companyId) {
      this.companyId = companyId;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }


}
