import { Component, OnInit } from '@angular/core';
import { Order, OrderService } from '../../services/order.service';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-management',
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.scss']
})
export class OrderManagementComponent implements OnInit {
  orders: Order[] = [];

  
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getAllOrders().subscribe(data => {
      this.orders = data;
    });
  }

  onStatusChange(event: Event, orderId: number): void {
    const target = event.target as HTMLSelectElement;
    const newStatus = target.value;
    if (newStatus) {
      this.updateOrderStatus(orderId, newStatus);
    }
  }

  updateOrderStatus(id: number, status: string): void {
    if (status) {
      this.orderService.updateOrderStatus(id, status).subscribe(updatedOrder => {
        // Handle the updated order
      });
    }
  }
}