package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.service.MockTransactionService;
import com.nm.tranproc.api.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRestControllerTest {

  @Test
  @DisplayName("Positive_Test_1 - Response code 200")
  void sendToTransactionServicePositiveTest()
      throws TransactionServiceException {

    TransactionService service =
        new MockTransactionService(TestType.GOOD_TEST);

    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();

    ResponseEntity<ResponseDTO> response =
        restController.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertNotNull(response.getBody());

    ResponseDTO responseDto = response.getBody();

    assertNotNull(responseDto.getResponseMessage());
    assertNotNull(responseDto.getResponseCode());
    assertEquals("200", responseDto.getResponseCode());
    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Negative_Test_2 - Response code 500")
  void sendToTransactionServiceNegativeTest()
      throws TransactionServiceException {

    TransactionService service =
        new MockTransactionService(TestType.NEGATIVE_TEST);

    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();

    ResponseEntity<ResponseDTO> response =
        restController.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertNotNull(response.getBody());

    ResponseDTO responseDto = response.getBody();

    assertNotNull(responseDto.getResponseMessage());
    assertNotNull(responseDto.getResponseCode());
    assertEquals("500", responseDto.getResponseCode());
    assertEquals(500, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Negative_Test_3 - TransactionServiceException")
  void sendToTransactionServiceExceptionTest() {

    TransactionService service =
        new MockTransactionService(TestType.THROWS_EXCEPTIONS);

    TransactionRestController restController =
        new TransactionRestController(service);

    Request request = TestRequestMaker.makeRequest();

    ResponseEntity<ResponseDTO> response =
        restController.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("500", response.getBody().getResponseCode());
    assertNotNull(response.getBody().getResponseMessage());
  }

  @Test
  @DisplayName("Positive_Test_4 - writeToDb")
  void writeToDbPositiveTest()
      throws NumberFormatException,
      IllegalArgumentException,
      NullPointerException {

    TransactionService service =
        new MockTransactionService(TestType.GOOD_TEST);

    Request request = TestRequestMaker.makeRequest();

    assertNotNull(request);

    service.writeToDb(request);

    assertNotNull(service);
  }

  @Test
  @DisplayName("Negative_Test_5 - writeToDb throws NumberFormatException")
  void writeToDbExceptionTest() {

    TransactionService service =
        new MockTransactionService(TestType.THROWS_EXCEPTIONS);

    Request request = TestRequestMaker.makeRequest();

    assertNotNull(request);

    assertThrows(
        Exception.class,
        () -> service.writeToDb(request)
    );
  }
}