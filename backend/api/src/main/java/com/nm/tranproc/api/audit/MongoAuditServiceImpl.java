package com.nm.tranproc.api.audit;

import com.nm.tranproc.api.response.ResponseDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class MongoAuditServiceImpl implements  MongoAuditService{

  private final MongoTemplate mongoTemplate;

  public MongoAuditServiceImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void writeToAudit(ResponseDTO response) {
    mongoTemplate.save(response, "transaction_audit");
  }

//  @Override
//  public List<String> findAllTransactionGuids() {
//    return mongoTemplate.query(ResponseDTO.class)
//        .inCollection("transaction_audit")
//        .distinct("transactionGuid")
//        .as(String.class)
//        .all();
//  }
@Override
public List<String> findAllTransactionGuids() {

  Query query = new Query();

  return mongoTemplate.findDistinct(
      query,
      "transactionGuid",
      "transaction_audit",
      ResponseDTO.class,
      String.class
  );
}

  @Override
  public ResponseDTO findByTransactionGuid(String transactionGuid) {

    Query query = new Query(
        Criteria.where("transactionGuid").is(transactionGuid)
    );

    return mongoTemplate.findOne(
        query,
        ResponseDTO.class,
        "transaction_audit"
    );
  }

}
