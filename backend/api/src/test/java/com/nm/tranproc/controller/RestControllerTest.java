package com.nm.tranproc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestControllerTest {

     @Test
     void sendToTransactionProcessor_shouldReturnResponse() {

         Request request = new Request();

         Response response = restController.sendToTransactionProcessor(request);

         assertNotNull(response);
     }

}