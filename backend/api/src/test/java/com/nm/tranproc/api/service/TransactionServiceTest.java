package com.nm.tranproc.api.service;
import com.nm.tranproc.api.controller.TestRequestMaker;
import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.producer.MockProducer;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.repository.MockTransactionRepository;
import com.nm.tranproc.api.repository.TransactionRepository;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TransactionServiceTest {
  TransactionRepository repository = mock(TransactionRepository.class);


  @Test
  @DisplayName("Positive_Test_1 - Response code 200")
  void sendToTransactionProcessorPositiveTest() throws TransactionServiceException {
    Producer producer = new MockProducer(TestType.GOOD_TEST);
     TransactionService service = new TransactionServiceImpl(producer,repository);

    Request request = TestRequestMaker.makeRequest();
    ResponseDTO response = service.sendToTransactionProcessor(request);
    assertNotNull(response);
    String responseMessage = response.getResponseMessage();
    String responseCode = response.getResponseCode();
    assertNotNull(responseMessage);
    assertNotNull(responseCode);
    assertEquals("200", response.getResponseCode());
  }


  @Test
  @DisplayName("Negative_Test_2 - Response code 500")
  void sendToTransactionProcessorNegativeTest() throws TransactionServiceException {
    Producer producer = new MockProducer(TestType.NEGATIVE_TEST);
    TransactionService service = new TransactionServiceImpl(producer,repository);

    Request request = TestRequestMaker.makeRequest();
    ResponseDTO response = service.sendToTransactionProcessor(request);
    assertNotNull(response);
    String responseMessage = response.getResponseMessage();
    String responseCode = response.getResponseCode();
    assertNotNull(responseMessage);
    assertNotNull(responseCode);
    assertEquals("500", response.getResponseCode());
  }


  @Test
  @DisplayName("Negative_Test_3 - TransactionServiceException")
  void sendToTransactionProcessorExceptionTest() {
    Producer producer = new MockProducer(TestType.THROWS_EXCEPTIONS);
    TransactionService service = new TransactionServiceImpl(producer,repository);

    Request request = TestRequestMaker.makeRequest();
    assertThrows(
        TransactionServiceException.class,
        () -> service.sendToTransactionProcessor(request)
    );
  }
}
