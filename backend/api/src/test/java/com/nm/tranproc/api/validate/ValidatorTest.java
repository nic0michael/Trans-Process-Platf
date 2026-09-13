package com.nm.tranproc.api.validate;

import com.nm.tranproc.api.controller.TestRequestMaker;
import com.nm.tranproc.api.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
  @Test
  void validate_shouldReturnTrueForValidRequest() {

    Request request = TestRequestMaker.makeRequest();

    Validator validator = new Validator();

    boolean result = validator.validate(request);

    assertTrue(result);
    assertEquals("", validator.getMessage());
  }

  @Test
  void validate_shouldReturnFalseForValidRequest() {

    Request request = TestRequestMaker.makeRequest();
    request.setAmount(null);

    Validator validator = new Validator();

    boolean result = validator.validate(request);

    assertFalse(result);
    assertFalse(validator.getMessage().isEmpty());
  }
}
