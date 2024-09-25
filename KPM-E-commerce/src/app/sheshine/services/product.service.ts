import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
export interface Product {
  shine: boolean;
  sheShine: boolean;
  id: number;
  userId: number; // ID of the user who owns the cart
  productId: number;
  name: string;
  howToUse: string;
  description: string;
  keybenefits: string;
  benefit: string;
  color: string;
  size: string;
  mrp: number;
  discount: number;
  price: number;
  image: string;
  thumbnail: string;
  image1: string;
  category?: string;
  suitable: string;
  subCategory?: string;
  quantity?: number;
  cards?: Array<{ image: string; title: string; text: string }>;
  images?: string[];
  threeDImages?: string[];
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private productSubject: BehaviorSubject<Product | null> =
    new BehaviorSubject<Product | null>(null);
  public product$: Observable<Product | null> =
    this.productSubject.asObservable();

  private productsUrl = 'http://192.168.1.20:8080/api/products/json';
  private shineProductsUrl = 'http://192.168.1.20:8080/api/products/json';

  constructor(private http: HttpClient) {
    this.loadProduct();
  }

  private loadProduct() {
    this.http
      .get<Product[]>(this.productsUrl)
      .pipe(
        map((data: Product[]) => {
          return data[0]; // Get the first product for now
        })
      )
      .subscribe((product) => {
        this.productSubject.next(product);
      });
  }

  setProduct(product: Product) {
    this.productSubject.next(product);
  }

  getProduct(): Observable<Product | null> {
    return this.product$;
  }

  getProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.productsUrl)
      .pipe(
        map((products) =>
          products.filter((product) => product.sheShine === true)
        )
      );
  }

  getShineProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.shineProductsUrl)
      .pipe(
        map((products) => products.filter((product) => product.shine === true))
      );
  }

  // Method to get products from both sources combined
  getProductsFromBothSources(): Observable<Product[]> {
    return forkJoin([this.getProducts(), this.getShineProducts()]).pipe(
      map(([products, shineProducts]) => [...products, ...shineProducts])
    );
  }

  getProductById(id: number): Observable<Product> {
    return this.getProductsFromBothSources().pipe(
      map((products) => products.find((product) => product.id === id)!)
    );
  }

  searchProducts(query: string): Observable<Product[]> {
    return this.getProductsFromBothSources().pipe(
      map((products) =>
        products.filter((product) =>
          product.name.toLowerCase().includes(query.toLowerCase())
        )
      )
    );
  }
}
