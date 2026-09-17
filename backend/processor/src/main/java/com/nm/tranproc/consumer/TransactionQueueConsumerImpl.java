package com.nm.tranproc.consumer;

import com.nm.tranproc.service.TransactionProcessorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueConsumerImpl implements TransactionQueueConsumer{
  final TransactionProcessorService processorService;

  public TransactionQueueConsumerImpl(TransactionProcessorService processorService){
    this.processorService = processorService;
  }

  @Override
  @KafkaListener(
      topics = "transaction-topic",
      groupId = "transaction-processor"
  )
  public void consumeTransaction(com.nm.tranproc.api.request.Request request) {
    processorService.writeToDb(request);
  }

}
