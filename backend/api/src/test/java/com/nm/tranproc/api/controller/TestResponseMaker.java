package com.nm.tranproc.api.controller;
import com.nm.tranproc.api.enums.TestType;
import com.nm.tranproc.api.response.ResponseDTO;
public class TestResponseMaker {

  public static ResponseDTO makeResponse(TestType testType) {

    ResponseDTO response = new ResponseDTO();
    if(testType.equals(TestType.GOOD_TEST)) {
      response.setResponseCode("200");
      response.setResponseMessage("Success");
    } else if(testType.equals(TestType.NEGATIVE_TEST)){
      response.setResponseCode("500");
      response.setResponseMessage("Failure");
    }
    response.setTimestamp("2026-05-22 15:22:34");
    response.setTransactionIndex("1234");
    response.setTransactionGuid("yfF5fr7jjjfjj88");
    response.setRequestId("23ff");
    response.setExternalSystemId("KKy34");
    response.setTransactionType("SALES");
    response.setCurrency("USD");
    response.setAmount("124.78");
    response.setReference("Book45");
    response.setTransactionDescription("Book:73 Magazine July 2025");
    response.setCompanyId("GH65");
    response.setStatus("Busy");
    return response;
  }

}
