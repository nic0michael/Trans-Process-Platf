package com.nm.tranproc.api.transform;

import com.nm.tranproc.api.dto.TransactionDto;
import com.nm.tranproc.api.entity.TransactionEntity;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public static ResponseDTO convertToResponseDTO(
      TransactionEntity transactionEntity) {

    ResponseDTO response = new ResponseDTO();

    response.setResponseCode("200");
    response.setResponseMessage(
        "Retrieved GUID Record from Database");

    response.setTimestamp(
        transactionEntity.getTimestamp()
            .format(
                DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
                )
            )
    );

    response.setTransactionIndex(
        String.valueOf(transactionEntity.getTransactionIndex())
    );

    response.setTransactionGuid(
        transactionEntity.getTransactionGuid()
    );

    response.setRequestId(
        transactionEntity.getRequestId()
    );

    response.setExternalSystemId(
        transactionEntity.getExternalSystemId()
    );

    response.setTransactionType(
        transactionEntity.getTransactionType()
    );

    response.setCurrency(
        transactionEntity.getCurrency()
    );

    response.setAmount(
        transactionEntity.getAmount().toString()
    );

    response.setReference(
        transactionEntity.getReference()
    );

    response.setTransactionDescription(
        transactionEntity.getTransactionDescription()
    );

    response.setCompanyId(
        String.valueOf(transactionEntity.getCompanyId())
    );

    response.setStatus(
        transactionEntity.getStatus()
    );

    return response;
  }
}
