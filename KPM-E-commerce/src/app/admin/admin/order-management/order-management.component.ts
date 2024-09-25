import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-order-management',
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.scss']
})
export class OrderManagementComponent implements OnInit {
  orders: any[] = [];
  userId: number | null = null;
  selectedStatus: string = '';  // userId will be fetched from localStorage

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.fetchOrders();
    
    // Get userId from localStorage
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      this.userId = parseInt(storedUserId, 10); // Convert string to number
    }
  }

  fetchOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (data) => this.orders = data,
      error: (err) => console.error('Error fetching orders:', err)
    });
  }


  updateStatus(orderId: number, newStatus: string): void {
    if (!newStatus) {
      alert('Please select a status before updating');
      return;
    }
  
    if (this.userId !== null) {
      // Ensure the correct orderId is being passed here
      this.orderService.updateOrderStatus(orderId, newStatus, this.userId).subscribe({
        next: (response) => {
          console.log('Order status updated:', response);
          this.fetchOrders(); // Refresh the order list after updating
        },
        error: (err) => console.error('Error updating order status:', err)
      });
    } else {
      console.error('User ID is not available');
    }
  }
  
}