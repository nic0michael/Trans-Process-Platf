package com.nm.tranproc.api.audit;

import com.nm.tranproc.api.response.ResponseDTO;

import java.util.List;

public interface MongoAuditService {
  void writeToAudit(ResponseDTO response);
  List<String> findAllTransactionGuids();
  ResponseDTO findByTransactionGuid(String transactionGuid);
}
