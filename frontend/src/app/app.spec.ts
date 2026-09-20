import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting
} from '@angular/common/http/testing';

import { App } from './app';

describe('App', () => {

  let component: App;
  let fixture: ComponentFixture<App>;
  let httpTesting: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
    httpTesting = TestBed.inject(HttpTestingController);

    fixture.detectChanges();
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('should create the application', () => {
    expect(component).toBeTruthy();
  });

  it('should contain the transaction form', () => {
    const form = fixture.nativeElement.querySelector('form');

    expect(form).toBeTruthy();
  });

  it('should contain a submit button', () => {
    const button = fixture.nativeElement.querySelector(
      'button[type="submit"]'
    );

    expect(button).toBeTruthy();
  });

  it('should submit a transaction to the API', () => {

    component.transaction = {
      timestamp: '2026-09-20 15:30:00',
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

    component.submitTransaction();

    const request = httpTesting.expectOne(
      'http://localhost:8080/api-v1/transactions'
    );

    expect(request.request.method).toBe('POST');

    expect(request.request.body).toEqual(
      component.transaction
    );

    request.flush({
      responseCode: '200',
      responseMessage: 'Transaction sent to Kafka',
      transactionGuid: 'cf48ac00-a29d-4572-84a4-8894895f9547'
    });
  });

});
