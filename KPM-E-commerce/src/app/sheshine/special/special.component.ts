import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ProductService, Product } from '../services/product.service';
import { PLATFORM_ID } from '@angular/core';

@Component({
  selector: 'app-special',
  templateUrl: './special.component.html',
  styleUrl: './special.component.scss',
})
export class SpecialComponent {
  products: any[] = [];
  specialProducts: any[] = [];

  constructor(
    private http: HttpClient,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.fetchProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.specialProducts = this.products.filter(
          (product) => product.special === true
        );
      },
    });
  }

  fetchProducts(): Observable<any[]> {
    return this.http.get<any[]>('http://192.168.1.6:8080/api/products/json');
  }

  buyProduct(product: any): void {
    this.router.navigate(['/sheshine/view', product.id]);
  }
}
