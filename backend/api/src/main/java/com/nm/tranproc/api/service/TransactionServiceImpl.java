package com.nm.tranproc.api.service;

import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nm.tranproc.api.exception.TransactionServiceException;

@Service
public class TransactionServiceImpl implements TransactionService{
  private final Producer producer;

  @Autowired
  public TransactionServiceImpl(Producer producer) {
    this.producer = producer;
  }

  @Override
  public Response sendToTransactionProcessor(Request request)   throws TransactionServiceException{
    return producer.sendTransaction(request);
  }
}
