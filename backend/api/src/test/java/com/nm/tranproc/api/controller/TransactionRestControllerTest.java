package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.service.MockTransactionService;
import com.nm.tranproc.api.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRestControllerTest {


  @Test
  @DisplayName("Positive_Test_1 - Response code 200")
  void sendToTransactionServicePositiveTest() throws TransactionServiceException {
    TransactionService service = new MockTransactionService(TestType.GOOD_TEST);
    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();
    ResponseDTO response =
        restController.sendToTransactionProcessor(request);
    assertNotNull(response);
    String responseMessage = response.getResponseMessage();
    String responseCode = response.getResponseCode();
    assertNotNull(responseMessage);
    assertNotNull(responseCode);
    assertEquals("200", response.getResponseCode());
  }


  @Test
  @DisplayName("Negative_Test_2 - Response code 500")
  void sendToTransactionServiceNegativeTest() throws TransactionServiceException {
    TransactionService service = new MockTransactionService(TestType.NEGATIVE_TEST);
    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();
    ResponseDTO response =
        restController.sendToTransactionProcessor(request);
    assertNotNull(response);
    String responseMessage = response.getResponseMessage();
    String responseCode = response.getResponseCode();
    assertNotNull(responseMessage);
    assertNotNull(responseCode);
    assertEquals("500", response.getResponseCode());
  }

  @Test
  @DisplayName("Negative_Test_3 - TransactionServiceException")
  void sendToTransactionServiceExceptionTest()  {
    TransactionService service = new MockTransactionService(TestType.THROWS_EXCEPTIONS);
    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();

    assertThrows(
        TransactionServiceException.class,
        () -> restController.sendToTransactionProcessor(request)
    );

  }
}