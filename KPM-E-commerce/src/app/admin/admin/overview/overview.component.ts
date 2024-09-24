import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { OrderService } from '../../services/order.service';
import { CustomerService } from '../../services/customer.service';
import { Product } from '../../services/product.model';
import { DashboardService } from '../../services/dashboard.service';
@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  metrics: any = {};

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.dashboardService.getMetrics().subscribe(data => {
      this.metrics = data;
    });
  }
}

