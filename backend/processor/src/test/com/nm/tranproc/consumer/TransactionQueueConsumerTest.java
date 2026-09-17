package com.nm.tranproc.consumer;

import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.service.TransactionProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionQueueConsumerTest {
  @Mock
  private TransactionProcessorService processorService;

  @InjectMocks
  private TransactionQueueConsumerImpl consumer;

  @Test
  void consumeTransactionShouldCallProcessorService() {

    Request request = new Request();

    consumer.consumeTransaction(request);

    verify(processorService).writeToDb(request);
  }
}
