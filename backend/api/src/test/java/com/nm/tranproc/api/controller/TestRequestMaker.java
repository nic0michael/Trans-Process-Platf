package com.nm.tranproc.api.controller;


import com.nm.tranproc.api.request.Request;

public class TestRequestMaker {
  public static Request makeRequest(){
    Request request = new Request();
    request.setTimestamp ( "2026-05-22 15:22:34");
    request.setTransactionIndex ( "1234");
    request.setTransactionGuid ( "yfF5fr7jjjfjj88");
    request.setRequestId ( "23ff");
    request.setExternalSystemId ( "1234");
    request.setTransactionType ( "SALES");
    request.setCurrency ( "USD");
    request.setAmount ( "124.78");
    request.setReference ( "Book45");
    request.setTransactionDescription ( "Book:73 Magazine July 2025");
    request.setCompanyId ( "2345");
    request.setStatus ( "Busy");

    return request;
  }
}
