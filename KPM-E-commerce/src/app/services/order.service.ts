import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Order } from '../services/order.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private baseUrl = 'http://localhost:8080/api/orders'; // Update with your API URL

  constructor(private http: HttpClient) {}

  private ordersSubject = new BehaviorSubject<Order[]>([]);
  orders$ = this.ordersSubject.asObservable();



  // Place an order
  placeOrder(orderDTO: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/place`, orderDTO);
  }

  // Verify payment
  verifyPayment(paymentDetails: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/verifyPayment`, paymentDetails);
  }

  // Get orders by user
  getOrdersByUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/user/${userId}`);
  }

  // Get order by ID
  getOrderById(orderId: number): Observable<Order> {
    return this.http.get<Order>(`${this.baseUrl}/${orderId}`);
  }

  // Update order status
  updateOrderStatus(orderId: number, status: string, userId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${orderId}/status`, { status }, { params: { userId: userId.toString() } });
  }

  // Get all orders
  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}`);
  }
}
