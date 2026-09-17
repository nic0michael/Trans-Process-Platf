package com.nm.tranproc.api.audit;

import com.nm.tranproc.api.response.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MongoAuditServiceTest {
  private MongoTemplate mongoTemplate;

  private MongoAuditServiceImpl service;

  @BeforeEach
  void setUp() {
    mongoTemplate = mock(MongoTemplate.class);
    service = new MongoAuditServiceImpl(mongoTemplate);
  }

  @Test
  @DisplayName("Positive_Test_1 - Write audit record")
  void writeToAuditTest() {
    ResponseDTO response = new ResponseDTO();
    response.setResponseCode("200");
    response.setResponseMessage("Success");
    response.setTransactionGuid(
        "7ed070a0-92b6-4eb2-a007-bd2070f530f5"
    );
    service.writeToAudit(response);
    verify(mongoTemplate)
        .save(response, "transaction_audit");
  }

@Test
@DisplayName("Positive_Test_2 - Find all transaction GUIDs")
void findAllTransactionGuidsTest() {

  List<String> expectedGuids = List.of(
      "guid-001",
      "guid-002"
  );

  when(mongoTemplate.findDistinct(
      any(Query.class),
      eq("transactionGuid"),
      eq("transaction_audit"),
      eq(ResponseDTO.class),
      eq(String.class)
  )).thenReturn(expectedGuids);

  List<String> result =
      service.findAllTransactionGuids();

  assertNotNull(result);
  assertEquals(2, result.size());
  assertEquals(expectedGuids, result);

  verify(mongoTemplate).findDistinct(
      any(Query.class),
      eq("transactionGuid"),
      eq("transaction_audit"),
      eq(ResponseDTO.class),
      eq(String.class)
  );
}

  @Test
  @DisplayName("Positive_Test_3 - Find transaction by GUID")
  void findByTransactionGuidTest() {

    String transactionGuid =
        "7ed070a0-92b6-4eb2-a007-bd2070f530f5";

    ResponseDTO expectedResponse =
        new ResponseDTO();

    expectedResponse.setResponseCode("200");
    expectedResponse.setTransactionGuid(transactionGuid);

    when(mongoTemplate.findOne(
        any(Query.class),
        eq(ResponseDTO.class),
        eq("transaction_audit")))
        .thenReturn(expectedResponse);

    ResponseDTO result =
        service.findByTransactionGuid(transactionGuid);

    assertNotNull(result);
    assertEquals(
        transactionGuid,
        result.getTransactionGuid()
    );

    verify(mongoTemplate).findOne(
        any(Query.class),
        eq(ResponseDTO.class),
        eq("transaction_audit")
    );
  }

}
