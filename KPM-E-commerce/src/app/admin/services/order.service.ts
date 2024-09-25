import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/orders'; // Update with your API base URL

  constructor(private http: HttpClient) {}

  

//   // Get details of a single order by ID
//   getOrderById(id: number): Observable<Order> {
//     return this.http.get<Order>(`${this.baseUrl}${id}`);
//   }


//   getAllOrders(): Observable<any[]> {
//     return this.http.get<any[]>(this.baseUrl);
//   }

//   // Update order status
//   updateOrderStatus(orderId: number, status: string): Observable<any> {
//     const url = `${this.baseUrl}/${orderId}/status`;
//     return this.http.put(url, { status });
//   }
// }
getAllOrders(): Observable<any[]> {
  return this.http.get<any[]>(this.apiUrl);
}

// Update order status with orderId, status, and userId
updateOrderStatus(orderId: number, status: string, userId: number): Observable<any> {
  const url = `${this.apiUrl}/${orderId}/status`;
  
  // Append userId as a request parameter
  let params = new HttpParams().set('userId', userId.toString());
  
  return this.http.put(url, status, { params });
}
}


// Define the Order interface according to your backend's Order structure
// export interface Order {
//   [x: string]: any;
//   quantity: any;
//   productName: any;
//   id: number;
//   status: string;
//   orderDate: string;
//   customer: { id: number; name: string };
//   items: Array<{ product: { name: string }; quantity: number; price: number }>;
// }
