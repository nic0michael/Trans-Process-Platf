package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionRestControllerMokitoTest {

  @Mock
  private TransactionService service;

  @InjectMocks
  private TransactionRestController controller;

  @Test
  @DisplayName("Positive_Test_1 - Service returns response")
  void sendToTransactionProcessorPositiveTest()
      throws TransactionServiceException {

    Request request = TestRequestMaker.makeRequest();

    ResponseDTO expectedResponse = new ResponseDTO();
    expectedResponse.setResponseCode("200");
    expectedResponse.setResponseMessage("Success");

    when(service.sendToTransactionProcessor(request))
        .thenReturn(expectedResponse);

    ResponseEntity<ResponseDTO> response =
        controller.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("200", response.getBody().getResponseCode());
    assertEquals("Success", response.getBody().getResponseMessage());

    verify(service).sendToTransactionProcessor(request);
  }


  @Test
  @DisplayName("Negative_Test_2 - Service throws exception")
  void sendToTransactionProcessorExceptionTest()
      throws TransactionServiceException {

    Request request = TestRequestMaker.makeRequest();

    when(service.sendToTransactionProcessor(request))
        .thenThrow(new TransactionServiceException("Service error"));

    ResponseEntity<ResponseDTO> response =
        controller.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("500", response.getBody().getResponseCode());
    assertEquals("Service error", response.getBody().getResponseMessage());

    verify(service).sendToTransactionProcessor(request);
  }

}