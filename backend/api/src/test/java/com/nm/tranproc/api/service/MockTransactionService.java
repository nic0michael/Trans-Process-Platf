package com.nm.tranproc.api.service;

import com.nm.tranproc.api.controller.TestResponseMaker;
import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.exception.TransactionServiceException;

public class MockTransactionService implements TransactionService{
  TestType testType;
  public MockTransactionService(TestType testType){
    this.testType = testType;
  }


  @Override
  public ResponseDTO sendToTransactionProcessor(Request request) throws TransactionServiceException{
    ResponseDTO response = null;
     switch(testType){
       case GOOD_TEST -> response =  TestResponseMaker.makeResponse(TestType.GOOD_TEST);
       case NEGATIVE_TEST -> response =  TestResponseMaker.makeResponse(TestType.NEGATIVE_TEST);
       case THROWS_EXCEPTIONS -> throw new TransactionServiceException("Mocking failure");
     }
     return response;
  }
}
