import { TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import {
  TransactionService,
  TransactionRequest
} from './transaction.service';

describe('TransactionService', () => {

  let service: TransactionService;
  let httpTesting: HttpTestingController;

  const request: TransactionRequest = {
    timestamp: '2026-09-16 16:40:00',
    transactionIndex: '100007',
    transactionGuid: '',
    requestId: 'b82d4f61-93a7-4c25-ae18-607f3b9d5246',
    externalSystemId: 'WEB_PORTAL',
    transactionType: 'PURCHASE',
    currency: 'ZAR',
    amount: '3250.75',
    reference: 'TEST-TRANS-007',
    transactionDescription: 'Kafka purchase test',
    companyId: '2001',
    status: 'PENDING'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TransactionService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(TransactionService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('should send a POST request to the transaction API', () => {

    service.sendTransaction(request).subscribe();

    const httpRequest = httpTesting.expectOne(
      'http://localhost:8080/api-v1/transactions'
    );

    expect(httpRequest.request.method).toBe('POST');
    expect(httpRequest.request.body).toEqual(request);

    httpRequest.flush({
      responseCode: '200',
      responseMessage: 'Transaction sent to Kafka'
    });
  });

});
