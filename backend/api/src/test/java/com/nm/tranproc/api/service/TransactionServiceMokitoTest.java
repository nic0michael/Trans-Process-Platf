package com.nm.tranproc.api.service;

import com.nm.tranproc.api.controller.TestRequestMaker;
import com.nm.tranproc.api.entity.TransactionEntity;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceMokitoTest {

  @Mock
  private Producer producer;

  @Mock
  private TransactionRepository repository;

  @InjectMocks
  private TransactionServiceImpl service;


  @Test
  @DisplayName("Positive_Test_1 - Valid request")
  void sendToTransactionProcessorValidTest() throws Exception {

    Request request = TestRequestMaker.makeRequest();

    ResponseDTO expectedResponse = new ResponseDTO();
    expectedResponse.setResponseCode("200");
    expectedResponse.setResponseMessage("Success");

    when(repository.findAllByTransactionGuid(anyString()))
        .thenReturn(Collections.emptyList());

    when(producer.sendTransaction(any(Request.class)))
        .thenReturn(expectedResponse);

    ResponseDTO response =
        service.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertEquals("200", response.getResponseCode());
    assertEquals("Success", response.getResponseMessage());

    verify(repository).save(any(TransactionEntity.class));
    verify(producer).sendTransaction(any(Request.class));
  }


  @Test
  @DisplayName("Negative_Test_2 - Invalid request")
  void sendToTransactionProcessorInvalidTest() throws Exception {

    Request request = new Request();

    ResponseDTO response =
        service.sendToTransactionProcessor(request);

    assertNotNull(response);
    assertEquals("400", response.getResponseCode());
    assertNotNull(response.getResponseMessage());

    verifyNoInteractions(repository);
    verifyNoInteractions(producer);
  }


  @Test
  @DisplayName("Positive_Test_3 - New transaction")
  void writeToDbNewTransactionTest() {

    Request request = TestRequestMaker.makeRequest();

    when(repository.findAllByTransactionGuid(anyString()))
        .thenReturn(Collections.emptyList());

    assertDoesNotThrow(() ->
        service.writeToDb(request)
    );

    verify(repository).save(any(TransactionEntity.class));
  }


  @Test
  @DisplayName("Positive_Test_4 - Existing transaction")
  void writeToDbExistingTransactionTest() {

    Request request = TestRequestMaker.makeRequest();

    TransactionEntity existingEntity = new TransactionEntity();

    when(repository.findAllByTransactionGuid(anyString()))
        .thenReturn(List.of(existingEntity));

    assertDoesNotThrow(() ->
        service.writeToDb(request)
    );

    verify(repository).save(existingEntity);
  }


  @Test
  @DisplayName("Positive_Test_5 - Null repository result")
  void writeToDbNullResultTest() {

    Request request = TestRequestMaker.makeRequest();

    when(repository.findAllByTransactionGuid(anyString()))
        .thenReturn(null);

    assertDoesNotThrow(() ->
        service.writeToDb(request)
    );

    verify(repository).save(any(TransactionEntity.class));
  }
}