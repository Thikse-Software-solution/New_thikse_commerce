import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from './order.model'; // Assuming you have an Order interface or model

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://192.168.1.10:8080/api/orders'; // Update with your backend API URL

  constructor(private http: HttpClient) {}

  // Place an order
  createOrder(order: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/place`, order);
  }

  // Verify payment
  verifyPayment(paymentDetails: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/verifyPayment`, paymentDetails);
  }

  // Get all orders by user ID
  getOrdersByUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/user/${userId}`);
  }

  // Get order by ID
  getOrderById(orderId: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/${orderId}`);
  }

  // Update order status
  updateOrderStatus(orderId: number, status: string, userId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${orderId}/status`, { status, userId });
  }

  // Get all orders
  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl);
  }
}
