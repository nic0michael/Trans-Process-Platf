import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TransactionRequest {
  timestamp: string;
  transactionIndex: string;
  transactionGuid: string;
  requestId: string;
  externalSystemId: string;
  transactionType: string;
  currency: string;
  amount: string;
  reference: string;
  transactionDescription: string;
  companyId: string;
  status: string;
}

export interface TransactionResponse extends TransactionRequest {
  responseCode: string;
  responseMessage: string;
}

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api-v1/transactions';

  sendTransaction(
    request: TransactionRequest
  ): Observable<TransactionResponse> {

    return this.http.post<TransactionResponse>(
      this.apiUrl,
      request
    );
  }
}
