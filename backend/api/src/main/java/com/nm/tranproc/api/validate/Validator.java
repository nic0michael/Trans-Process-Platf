package com.nm.tranproc.api.validate;

import com.nm.tranproc.api.request.Request;
import org.apache.commons.lang3.StringUtils;

public class Validator {
  StringBuilder message = null;

  public Validator(){
    message = new StringBuilder();
  }
  public boolean validate(Request request){
    Boolean isValid = true;

    if(StringUtils.isEmpty(request.getTimestamp())){
      message.append("Timestamp is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getRequestId())){
      message.append("RequestId is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getExternalSystemId())){
      message.append("ExternalSystemId is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getTransactionType())){
      message.append("TransactionType is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getCurrency())){
      message.append("Currency is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getAmount())){
      message.append("Amount is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getReference())){
      message.append("Reference is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getTransactionDescription())){
      message.append("TransactionDescription is missing");
      message.append("\n");
      isValid = false;
    }
    if(StringUtils.isEmpty(request.getCompanyId())){
      message.append("CompanyId is missing");
      message.append("\n");
      isValid = false;
    }
    return isValid;
  }

  public String getMessage() {
    return message.toString();
  }
}
