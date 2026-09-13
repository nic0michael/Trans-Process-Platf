package com.nm.tranproc.api.service;

import com.nm.tranproc.api.enrich.Enricher;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.validate.Validator;
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
  public ResponseDTO sendToTransactionProcessor(Request request)   throws TransactionServiceException{
    Validator validator = new Validator();
    if(! validator.validate(request)){
      ResponseDTO response = new ResponseDTO();
      response.setResponseMessage(validator.getMessage());
      response.setResponseCode("400");
      return response;
    }
    request = Enricher.enrich(request);
    return producer.sendTransaction(request);
  }
}
