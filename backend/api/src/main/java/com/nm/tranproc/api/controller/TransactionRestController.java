package com.nm.tranproc.api.controller;

import com.nm.tranproc.api.exception.TransactionServiceException;
import com.nm.tranproc.api.request.Request;
import com.nm.tranproc.api.response.ResponseDTO;
import com.nm.tranproc.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-v1/transactions")
public class TransactionRestController {

  private final TransactionService service;

  @Autowired
  public TransactionRestController(TransactionService service) {
    this.service = service;
  }
  public ResponseEntity<ResponseDTO> sendToTransactionProcessor(
      @RequestBody Request request) {

    try {
      ResponseDTO response = service.sendToTransactionProcessor(request);

      int responseCode = Integer.parseInt(response.getResponseCode());

      return ResponseEntity
          .status(responseCode)
          .body(response);

    } catch (TransactionServiceException e) {

      ResponseDTO response = new ResponseDTO();
      response.setResponseCode("500");
      response.setResponseMessage(e.getMessage());

      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(response);
    }
  }


  @GetMapping("/{transactionGuid}")
  public ResponseEntity<ResponseDTO> getTransactionResults(
      @PathVariable String transactionGuid) {

    try {
      ResponseDTO response = service.getTransactionResults(transactionGuid);

      int responseCode = Integer.parseInt(response.getResponseCode());

      return ResponseEntity
          .status(responseCode)
          .body(response);

    } catch (Exception e) {

      ResponseDTO response = new ResponseDTO();
      response.setResponseCode("500");
      response.setResponseMessage(e.getMessage());

      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(response);
    }
  }



}