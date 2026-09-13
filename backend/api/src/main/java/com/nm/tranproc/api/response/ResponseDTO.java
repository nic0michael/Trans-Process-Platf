package com.nm.tranproc.api.response;

public class ResponseDTO {
  private String responseCode;
  private String responseMessage;
  private String timestamp;
  private String transactionIndex;
  private String transactionGuid;
  private String requestId;
  private String externalSystemId;
  private String transactionType;
  private String currency;
  private String amount;
  private String reference;
  private String transactionDescription;
  private String companyId;
  private String status;

  public ResponseDTO(){}

  public ResponseDTO(String responseCode, String responseMessage, String timestamp, String transactionIndex, String transactionGuid, String requestId, String externalSystemId, String transactionType, String currency, String amount, String reference, String transactionDescription, String companyId, String status) {
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
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
    return "Response{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage='" + responseMessage + '\'' +
        ", timestamp='" + timestamp + '\'' +
        ", transactionIndex='" + transactionIndex + '\'' +
        ", transactionGuid='" + transactionGuid + '\'' +
        ", requestId='" + requestId + '\'' +
        ", externalSystemId='" + externalSystemId + '\'' +
        ", transactionType='" + transactionType + '\'' +
        ", currency='" + currency + '\'' +
        ", amount='" + amount + '\'' +
        ", reference='" + reference + '\'' +
        ", transactionDescription='" + transactionDescription + '\'' +
        ", companyId='" + companyId + '\'' +
        ", status='" + status + '\'' +
        '}';
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getTransactionIndex() {
    return transactionIndex;
  }

  public void setTransactionIndex(String transactionIndex) {
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

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
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

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
