package com.nm.tranproc.api.transform;

import com.nm.tranproc.api.dto.TransactionDto;
import com.nm.tranproc.api.request.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transformer {

  public static TransactionDto convertToTransactionDto(Request request)
      throws NumberFormatException,
      IllegalArgumentException,
      NullPointerException {

    TransactionDto dto = new TransactionDto();

    dto.setTimestamp(LocalDateTime.parse(request.getTimestamp().replace(" ", "T")));
    dto.setTransactionIndex(Long.parseLong(request.getTransactionIndex()));
    dto.setTransactionGuid(request.getTransactionGuid());
    dto.setRequestId(request.getRequestId());
    dto.setExternalSystemId(request.getExternalSystemId());
    dto.setTransactionType(request.getTransactionType());
    dto.setCurrency(request.getCurrency());
    dto.setAmount(new BigDecimal(request.getAmount()));
    dto.setReference(request.getReference());
    dto.setTransactionDescription(request.getTransactionDescription());
    dto.setCompanyId(Long.parseLong(request.getCompanyId()));
    dto.setStatus(request.getStatus());

    return dto;
  }
}
