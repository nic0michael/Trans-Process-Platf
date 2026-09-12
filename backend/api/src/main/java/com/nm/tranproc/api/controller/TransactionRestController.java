package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.Response;
import com.nm.tranproc.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {
  private final TransactionService service;

  @Autowired
  public TransactionRestController(TransactionService service) {
    this.service = service;
  }

  public Response sendToTransactionProcessor(Request request) throws TransactionServiceException {
    return service.sendToTransactionProcessor( request);
  }
}