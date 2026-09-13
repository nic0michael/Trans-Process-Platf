package com.nm.tranproc.api.service;

import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.exception.TransactionServiceException;

public interface TransactionService {
  ResponseDTO sendToTransactionProcessor(Request request) throws TransactionServiceException;
}
