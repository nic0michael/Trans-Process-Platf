package com.nm.tranproc.api.producer;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;

public interface Producer {
  ResponseDTO sendTransaction(Request request) throws TransactionServiceException;
}
