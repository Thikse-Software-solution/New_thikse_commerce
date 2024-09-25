import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShippingDetailService {
  private baseUrl = 'http://192.168.1.10:8080/api/analytics/shipping-details';

  constructor(private http: HttpClient) {}

  getShippingDetail(orderId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${orderId}`);
  }
}
