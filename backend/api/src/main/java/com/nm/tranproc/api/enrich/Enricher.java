package com.nm.tranproc.api.enrich;

import com.nm.tranproc.api.request.Request;

import java.util.UUID;

public class Enricher {
  public static Request enrich(Request request){
    String guid = UUID.randomUUID().toString();
    request.setTransactionGuid(guid);
    return request;
  }
}
