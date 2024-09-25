import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-feature',
  templateUrl: './feature.component.html',
  styleUrl: './feature.component.scss',
})
export class FeatureComponent {
  products: any[] = [];
  featureProducts: any[] = [];

  constructor(
    private http: HttpClient,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.fetchProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.featureProducts = this.products.filter(
          (product) => product.feature === true
        );
      },
    });
  }

  fetchProducts(): Observable<any[]> {
    return this.http.get<any[]>('http://192.168.1.10:8080/api/products/json');
  }
  buyProduct(product: any): void {
    this.router.navigate(['/sheshine/view', product.id]);
  }
}
