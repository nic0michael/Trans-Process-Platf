package com.nm.tranproc.api.service;

import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.Response;
import com.nm.tranproc.api.exception.TransactionServiceException;

public interface TransactionService {
  Response sendToTransactionProcessor(Request request) throws TransactionServiceException;
}
