import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private baseUrl = 'http://192.168.1.6:8080/api/orders'; // Update with your API base URL

  constructor(private http: HttpClient) {}

  // Get a list of orders (if needed in the future)
  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.baseUrl);
  }

  // Get details of a single order by ID
  getOrderById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.baseUrl}${id}`);
  }

  // Update the status of an order
  updateOrderStatus(id: number, status: string): Observable<Order> {
    return this.http.put<Order>(`${this.baseUrl}/${id}/status`, status);
  }
}

// Define the Order interface according to your backend's Order structure
export interface Order {
  [x: string]: any;
  quantity: any;
  productName: any;
  id: number;
  status: string;
  orderDate: string;
  customer: { id: number; name: string };
  items: Array<{ product: { name: string }; quantity: number; price: number }>;
}
