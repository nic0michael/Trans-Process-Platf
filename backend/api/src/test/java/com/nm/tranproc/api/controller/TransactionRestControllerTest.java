package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.producer.MockProducer;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.service.MockTransactionService;
import com.nm.tranproc.api.service.TransactionService;
import com.nm.tranproc.api.service.TransactionServiceImpl;
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




//    @Test
//    @DisplayName("Positive_Test_4 - writeToDb")
//    void writeToDbPositiveTest() {
//
//      Producer producer = new MockProducer(TestType.GOOD_TEST);
//      TransactionService service = new MockTransactionService(TestType.GOOD_TEST);
////          new TransactionServiceImpl(producer,repository);
//
//      Request request = TestRequestMaker.makeRequest();
//
//      assertDoesNotThrow(() -> service.writeToDb(request));
//    }

//    @Test
//    @DisplayName("Negative_Test_5 - writeToDb throws NumberFormatException")
//    void writeToDbExceptionTest() {
//
//      Producer producer = new MockProducer(TestType.THROWS_EXCEPTIONS);
//      TransactionService service = new TransactionServiceImpl(producer,repository);
//
//      Request request = TestRequestMaker.makeRequest();
//
//      assertThrows(
//          Exception.class,
//          () -> service.writeToDb(request)
//      );
//    }

  }

  @Test
  @DisplayName("Positive_Test_4 - writeToDb")
  void writeToDbPositiveTest() throws NumberFormatException, IllegalArgumentException, NullPointerException {

    TransactionService service = new MockTransactionService(TestType.GOOD_TEST);
    Request request = TestRequestMaker.makeRequest();
    assertNotNull(request);
    service.writeToDb(request);
    assertNotNull(service);

  }

  @Test
  @DisplayName("Negative_Test_5 - writeToDb throws NumberFormatException")
  void writeToDbExceptionTest() {
    TransactionService service = new MockTransactionService(TestType.THROWS_EXCEPTIONS);
    Request request = TestRequestMaker.makeRequest();
    assertNotNull(request);
    assertThrows(
        Exception.class,
        () -> service.writeToDb(request)
    );

  }

}