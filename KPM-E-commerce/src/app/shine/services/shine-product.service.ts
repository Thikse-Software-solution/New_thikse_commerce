import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description: string;
  keybenefit: string;
  howToUse: string;
  size: string;
  mrp: number;
  discount: number;
  price: number;
  image: string;
  image1: string;
  category?: string;
  subcategory?: string;
  quantity?: number;
  thumbnail: string;
  cards?: Array<{ image: string; title: string; text: string }>;
  images?: string[];
  threeDImages?: string[]; // Array for storing 360° images
}

@Injectable({
  providedIn: 'root',
})
export class ShineProductService {
  private baseUrl: string = 'http://localhost:8080/api/products/json'; // Path to your JSON file

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.baseUrl)
      .pipe(
        map((products: any[]) =>
          products.filter((product) => product.shine === true)
        )
      );
  }

  // Fetch products by subcategory
  getProductsBySubcategory(subcategory: string): Observable<Product[]> {
    return new Observable((observer) => {
      this.getAllProducts().subscribe((products) => {
        const filteredProducts = products.filter(
          (product) => product.subcategory === subcategory
        );
        observer.next(filteredProducts);
        observer.complete();
      });
    });
  }
  getProductsByCategory(category: string): Observable<Product[]> {
    return new Observable((observer) => {
      this.getAllProducts().subscribe((products) => {
        const filteredProducts = products.filter(
          (product) => product.category === category
        );
        observer.next(filteredProducts);
        observer.complete();
      });
    });
  }

  // Fetch a product by its ID
  getProductById(id: number): Observable<Product> {
    return new Observable((observer) => {
      this.getAllProducts().subscribe((products) => {
        const product = products.find((p) => p.id === id);
        observer.next(product);
        observer.complete();
      });
    });
  }
}
