package com.nm.tranproc.api.repository;

import com.nm.tranproc.api.entity.TransactionEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockTransactionRepository implements TransactionRepository{
  @Override
  public List<TransactionEntity> findAllByTransactionGuid(String transactionGuid) {
    return null;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends TransactionEntity> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<TransactionEntity> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public TransactionEntity getOne(Long aLong) {
    return null;
  }

  @Override
  public TransactionEntity getById(Long aLong) {
    return null;
  }

  @Override
  public TransactionEntity getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends TransactionEntity> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends TransactionEntity> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends TransactionEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> S save(S entity) {
    return null;
  }

  @Override
  public <S extends TransactionEntity> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<TransactionEntity> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<TransactionEntity> findAll() {
    return null;
  }

  @Override
  public List<TransactionEntity> findAllById(Iterable<Long> longs) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(TransactionEntity entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends TransactionEntity> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<TransactionEntity> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<TransactionEntity> findAll(Pageable pageable) {
    return null;
  }
}
