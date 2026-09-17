package com.nm.tranproc.service;

public interface TransactionProcessorService {
  public void writeToDb(com.nm.tranproc.api.request.Request request);
}
