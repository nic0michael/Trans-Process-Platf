package com.nm.tranproc.api.service;

import com.nm.tranproc.api.dto.TransactionDto;
import com.nm.tranproc.api.enrich.Enricher;
import com.nm.tranproc.api.entity.TransactionEntity;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.repository.TransactionRepository;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.transform.Transformer;
import com.nm.tranproc.api.validate.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nm.tranproc.api.exception.TransactionServiceException;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
  private final Producer producer;
  private final TransactionRepository repository;

  @Autowired
  public TransactionServiceImpl(Producer producer, TransactionRepository repository) {
    this.producer = producer;
    this.repository = repository;
  }

  @Override
  public ResponseDTO sendToTransactionProcessor(Request request)   throws TransactionServiceException{
    Validator validator = new Validator();
    if(! validator.validate(request)){
      ResponseDTO response = new ResponseDTO();
      response.setResponseMessage(validator.getMessage());
      response.setResponseCode("400");
      return response;
    }
    request = Enricher.enrich(request);
    writeToDb(request);
    return producer.sendTransaction(request);
  }

  /**
   * IF RECORD WITH SAME ExternalSystemId EXISTS IN DB USE THAT RECORD
   * IF NOT EXISTS AND RECORD WITH SAME TransactionGuid EXISTS IN DB USE THAT RECORD
   * OTHERWISE CREATE A NEW RECORD
   *
   * @param request
   * @throws NumberFormatException
   * @throws IllegalArgumentException
   * @throws NullPointerException
   */
  @Override
  public void writeToDb(Request request)
      throws NumberFormatException,
      IllegalArgumentException,
      NullPointerException {

    TransactionEntity transactionEntity = null;
    TransactionDto dto = Transformer.convertToTransactionDto(request);
    List<TransactionEntity> externalSystemIdEntities = repository.findAllByExternalSystemId(dto.getExternalSystemId());
    List<TransactionEntity> guidEntities = repository.findAllByTransactionGuid(dto.getTransactionGuid());
    if(null==externalSystemIdEntities || externalSystemIdEntities.isEmpty() && null==guidEntities || guidEntities.isEmpty()){
      transactionEntity = new TransactionEntity();
    } else if(!externalSystemIdEntities.isEmpty()){
      transactionEntity = externalSystemIdEntities.get(0);
    } else if(!guidEntities.isEmpty()){
      transactionEntity = guidEntities.get(0);
    }

    transactionEntity.setTransactionDescription(dto.getTransactionDescription());
    transactionEntity.setTimestamp(dto.getTimestamp());
    transactionEntity.setTransactionGuid(dto.getTransactionGuid());
    transactionEntity.setRequestId(dto.getRequestId());
    transactionEntity.setExternalSystemId(dto.getExternalSystemId());
    transactionEntity.setTransactionType(dto.getTransactionType());
    transactionEntity.setCurrency(dto.getCurrency());
    transactionEntity.setAmount(dto.getAmount());
    transactionEntity.setReference(dto.getReference());
    transactionEntity.setTransactionDescription(dto.getTransactionDescription());
    transactionEntity.setCompanyId(dto.getCompanyId());
    transactionEntity.setStatus(dto.getStatus());

    repository.save(transactionEntity);


  }
}
