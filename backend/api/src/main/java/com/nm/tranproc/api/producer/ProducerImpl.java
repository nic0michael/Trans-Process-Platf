package com.nm.tranproc.api.producer;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerImpl implements Producer {

  private final KafkaTemplate<String, Request> kafkaTemplate;

  public ProducerImpl(KafkaTemplate<String, Request> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public ResponseDTO sendTransaction(Request request)
      throws TransactionServiceException {

    try {
      kafkaTemplate.send("transaction-topic", request);

      ResponseDTO response = new ResponseDTO();
      response.setResponseCode("200");
      response.setResponseMessage("Transaction sent to Kafka");

      return response;

    } catch (Exception e) {
      throw new TransactionServiceException(
          "Failed to send transaction to Kafka: " + e.getMessage());
    }
  }
}