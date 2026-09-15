package com.nm.tranproc.api.producer;
import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProducerImpl implements Producer {

  @Override
  public ResponseDTO sendTransaction(Request request) throws TransactionServiceException {
    return null;
  }
}