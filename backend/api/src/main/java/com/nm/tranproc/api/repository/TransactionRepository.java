package com.nm.tranproc.api.repository;
import com.nm.tranproc.api.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository   extends
    JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findAllByTransactionGuid(String transactionGuid);
  List<TransactionEntity> findAllByExternalSystemId(String externalSystemId);

}
