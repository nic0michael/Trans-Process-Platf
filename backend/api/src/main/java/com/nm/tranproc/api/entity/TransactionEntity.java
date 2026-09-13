package com.nm.tranproc.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_index")
  private Long transactionIndex;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @Column(name = "transaction_guid", nullable = false, unique = true)
  private String transactionGuid;

  @Column(name = "request_id", nullable = false, unique = true)
  private String requestId;

  @Column(name = "external_system_id", nullable = false)
  private String externalSystemId;

  @Column(name = "transaction_type", nullable = false)
  private String transactionType;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "amount", nullable = false, precision = 19, scale = 4)
  private BigDecimal amount;

  @Column(name = "reference", nullable = false, length = 100)
  private String reference;

  @Column(name = "transaction_description", length = 500)
  private String transactionDescription;

  @Column(name = "company_id", nullable = false)
  private Long companyId;

  @Column(name = "status", nullable = false)
  private String status;

  public Long getTransactionIndex() {
    return transactionIndex;
  }

  public void setTransactionIndex(Long transactionIndex) {
    this.transactionIndex = transactionIndex;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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