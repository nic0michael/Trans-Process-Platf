package com.nm.tranproc.api.producer;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerImplTest {

  @Mock
  private KafkaTemplate<String, Request> kafkaTemplate;

  @InjectMocks
  private ProducerImpl producer;

  @Test
  void sendTransactionPositiveTest()
      throws TransactionServiceException {

    Request request = new Request();

    CompletableFuture<SendResult<String, Request>> future =
        CompletableFuture.completedFuture(null);

    when(kafkaTemplate.send("transaction-topic", request))
        .thenReturn(future);

    ResponseDTO response =
        producer.sendTransaction(request);

    assertNotNull(response);
    assertEquals("200", response.getResponseCode());
    assertEquals(
        "Transaction sent to Kafka",
        response.getResponseMessage()
    );

    verify(kafkaTemplate)
        .send("transaction-topic", request);
  }

  @Test
  void sendTransactionKafkaExceptionTest() {

    Request request = new Request();

    when(kafkaTemplate.send("transaction-topic", request))
        .thenThrow(
            new RuntimeException("Kafka unavailable")
        );

    TransactionServiceException exception =
        assertThrows(
            TransactionServiceException.class,
            () -> producer.sendTransaction(request)
        );

    assertTrue(
        exception.getMessage()
            .contains(
                "Failed to send transaction to Kafka"
            )
    );

    verify(kafkaTemplate)
        .send("transaction-topic", request);
  }

  @Test
  void sendTransactionSendsCorrectRequestTest()
      throws TransactionServiceException {

    Request request = new Request();

    CompletableFuture<SendResult<String, Request>> future =
        CompletableFuture.completedFuture(null);

    when(kafkaTemplate.send(
        eq("transaction-topic"),
        eq(request)))
        .thenReturn(future);

    producer.sendTransaction(request);

    verify(kafkaTemplate, times(1))
        .send("transaction-topic", request);
  }
}