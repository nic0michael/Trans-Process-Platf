package com.nm.tranproc.consumer;

public interface TransactionQueueConsumer {
  public void consumeTransaction(com.nm.tranproc.api.request.Request request);

}
