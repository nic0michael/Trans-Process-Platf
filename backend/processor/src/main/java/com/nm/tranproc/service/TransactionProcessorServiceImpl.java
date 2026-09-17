package com.nm.tranproc.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionProcessorServiceImpl implements TransactionProcessorService{
  protected com.nm.tranproc.api.service.TransactionService transactionService;

  public TransactionProcessorServiceImpl(com.nm.tranproc.api.service.TransactionService transactionService){
    this.transactionService = transactionService;
  }

  @Override
  public void writeToDb(com.nm.tranproc.api.request.Request request) {
    transactionService.writeToDb(request);
  }

}
