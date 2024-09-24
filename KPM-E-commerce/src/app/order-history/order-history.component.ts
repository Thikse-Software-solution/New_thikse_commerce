import { Component, OnInit } from '@angular/core';
import { OrderService } from '../services/order.service';
import { Order } from '../services/order.model'; 

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit {
  orders: any[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    // Subscribe to the orders observable from the service
    this.orderService.orders$.subscribe(orders => {
      this.orders = orders;
    });

    //   // Check if any orders are beyond the cancellation period
    //   this.orderService.checkCancellationAvailability();
    // }

    // // Cancel the order by updating its status
    // cancelOrder(orderId: number): void {
    //   this.orderService.cancelOrder(orderId);
    // }

    // // Mark the order as received by updating its status
    // markAsReceived(orderId: number): void {
    //   this.orderService.markAsReceived(orderId);
    // }

    // // Return the order by updating its status
    // returnOrder(orderId: number): void {
    //   this.orderService.returnOrder(orderId);
    // }

    // // Check if the order status should trigger the support message
    // shouldShowSupportMessage(status: string): boolean {
    //   return status === 'Cancelled' || status === 'Returned';
    // }
  }
}