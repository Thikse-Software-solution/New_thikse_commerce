import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PendingOrderService {
  private baseUrl = 'http://192.168.1.6:8080/api/analytics/pending-orders';

  constructor(private http: HttpClient) {}

  getPendingOrder(orderId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${orderId}`);
  }
}
