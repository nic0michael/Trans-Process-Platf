package com.nm.tranproc.api.enrich;

import com.nm.tranproc.api.controller.TestRequestMaker;
import com.nm.tranproc.api.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnricherTest {
  @Test
  void validate_shouldReturnTrueForValidRequest() {

    Request request = TestRequestMaker.makeRequest();
    request.setTransactionGuid(null);
    request = Enricher.enrich(request);
    String guid = request.getTransactionGuid();

    assertNotNull(guid);
  }
}
