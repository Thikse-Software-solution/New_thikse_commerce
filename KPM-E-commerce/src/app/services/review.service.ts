import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from './review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private apiUrl = 'http://192.168.1.6:8080/api/reviews'; // Your API base URL

  constructor(private http: HttpClient) {}

  addReview(review: Review): Observable<Review> {
    return this.http.post<Review>(`${this.apiUrl}/add`, review);
  }

  getReviewsByProductId(productId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/product/${productId}`);
  }

  getAverageRating(productId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/product/${productId}/average`);
  }
}
