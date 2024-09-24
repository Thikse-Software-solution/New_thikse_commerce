import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CustomerBehaviorService {
  private baseUrl = 'http://192.168.1.6:8080/api/analytics/customer-behavior';

  constructor(private http: HttpClient) {}

  getCustomerBehavior(customerId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${customerId}`);
  }
}
