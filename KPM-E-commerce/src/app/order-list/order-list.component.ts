import { Component, OnInit } from '@angular/core';
import { OrderService } from '../services/order.service';
import { Order } from '../services/order.model';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    // Fetch user ID from localStorage
    const userId = localStorage.getItem('userId'); 
    
    if (userId) {
      this.orderService.getOrdersByUser(parseInt(userId)).subscribe(
        (data: Order[]) => {
          this.orders = data;
        },
        (error) => {
          console.error('Error fetching orders', error);
        }
      );
    } else {
      console.error('User ID not found in localStorage');
    }
  }
}
