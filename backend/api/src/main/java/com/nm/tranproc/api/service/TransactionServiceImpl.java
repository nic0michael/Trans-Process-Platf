package com.nm.tranproc.api.service;

import com.nm.tranproc.api.audit.MongoAuditService;
import com.nm.tranproc.api.dto.TransactionDto;
import com.nm.tranproc.api.enrich.Enricher;
import com.nm.tranproc.api.entity.TransactionEntity;
import com.nm.tranproc.api.producer.Producer;
import com.nm.tranproc.api.repository.TransactionRepository;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.transform.Transformer;
import com.nm.tranproc.api.validate.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nm.tranproc.api.exception.TransactionServiceException;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
  private final Producer producer;
  private final TransactionRepository repository;
  private final MongoAuditService mongoAuditService;

  private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

  @Autowired
  public TransactionServiceImpl(Producer producer, TransactionRepository repository,  MongoAuditService mongoAuditService) {
    this.producer = producer;
    this.repository = repository;
    this.mongoAuditService = mongoAuditService;
  }

  @Override
  public ResponseDTO sendToTransactionProcessor(Request request)   throws TransactionServiceException{
    Validator validator = new Validator();
    if(! validator.validate(request)){
      logger.info("Validation Failed" + validator.getMessage());
      ResponseDTO response = Transformer.convertRequestToResponseDTO(request);
      response.setResponseMessage(validator.getMessage());
      response.setResponseCode("400");
      logger.info("Writing failed response to Audit");
      writeToAudit(response);

      return response;
    }
    logger.info("Validation succeeded");
    request = Enricher.enrich(request);
    ResponseDTO response = Transformer.convertRequestToResponseDTO(request);
    writeToAudit(response);
    logger.info("Writing request to DB");
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
      logger.info("Entity not found creating it");
    } else if(!externalSystemIdEntities.isEmpty()){
      transactionEntity = externalSystemIdEntities.get(0);
      logger.info("Entity found updating it");
    } else if(!guidEntities.isEmpty()){
      transactionEntity = guidEntities.get(0);
      logger.info("Entity found updating it");
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
    logger.info("Saving Entity");
    repository.save(transactionEntity);

  }

  @Override
  public ResponseDTO getTransactionResults(String transactionGuid) {
    ResponseDTO responseDTO = new ResponseDTO();
    List<TransactionEntity> guidEntities = repository.findAllByTransactionGuid(transactionGuid);
    if (null != guidEntities && ! guidEntities.isEmpty()){
      TransactionEntity transactionEntity = guidEntities.get(0);
      responseDTO = Transformer.convertToResponseDTO(transactionEntity);
      logger.info("Entity found returning it");
    } else{
      responseDTO.setResponseCode("200");
      responseDTO.setResponseMessage("Did not find Guid: "+ transactionGuid);;
      logger.info("Did not find Guid: "+ transactionGuid);
    }
    return responseDTO;
  }


  private void writeToAudit(ResponseDTO responseDTO) {
    logger.info("Writing Audit to mongo");
    mongoAuditService.writeToAudit(responseDTO);
  }
}
