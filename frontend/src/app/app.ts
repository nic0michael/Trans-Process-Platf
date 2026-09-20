import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import {
  TransactionRequest,
  TransactionResponse,
  TransactionService
} from './transaction.service';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  private readonly transactionService = inject(TransactionService);

  transaction: TransactionRequest = {
    timestamp: this.getCurrentTimestamp(),
    transactionIndex: '',
    transactionGuid: '',
    requestId: '',
    externalSystemId: 'WEB_PORTAL',
    transactionType: 'PURCHASE',
    currency: 'ZAR',
    amount: '',
    reference: '',
    transactionDescription: '',
    companyId: '',
    status: 'PENDING'
  };

  response: TransactionResponse | null = null;
  errorMessage = '';

  private getCurrentTimestamp(): string {
    const now = new Date();

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  submitTransaction(): void {

    this.response = null;
    this.errorMessage = '';

    this.transactionService
      .sendTransaction(this.transaction)
      .subscribe({
        next: (response) => {
          this.response = response;
        },
        error: (error) => {
          console.error('Transaction request failed:', error);

          if (error.status) {
            this.errorMessage =
              `HTTP ${error.status}: ` +
              (error.error?.responseMessage ??
               error.message ??
               'Backend request failed');
          } else {
            this.errorMessage =
              'Unable to connect to the backend. Check that Spring Boot is running and that CORS is configured.';
          }
        }
      });
  }
}
