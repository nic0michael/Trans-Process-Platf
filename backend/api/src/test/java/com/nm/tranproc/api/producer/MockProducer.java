package com.nm.tranproc.api.producer;

import com.nm.tranproc.api.controller.TestResponseMaker;
import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.Response;

public class MockProducer implements Producer{
  TestType testType;
  public MockProducer(TestType testType){
    this.testType = testType;
  }
  @Override
  public Response sendTransaction(Request request) throws TransactionServiceException {
    Response response = null;
    switch(testType){
      case GOOD_TEST -> response =  TestResponseMaker.makeResponse(TestType.GOOD_TEST);
      case NEGATIVE_TEST -> response =  TestResponseMaker.makeResponse(TestType.NEGATIVE_TEST);
      case THROWS_EXCEPTIONS -> throw new TransactionServiceException("Mocking failure");
    }
    return response;
  }
}
