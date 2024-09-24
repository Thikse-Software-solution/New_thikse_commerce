import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { Review } from '../services/review.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {
  reviews: Review[] = [];
  maxStars: number = 5;
  averageRating: number | null = null;
  newReview: Review = { rating: 0, comment: '', user: { id: 0 }, productId: 1, userName: '', userAvatar:'' }; // Adjust initial values

  constructor(private reviewService: ReviewService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Get the dynamic product ID from the URL
    this.route.paramMap.subscribe(params => {
      const productId = Number(params.get('id')); // Assuming 'id' is the name of the route param for product ID
      if (productId) {
        this.fetchReviews(productId);
        this.fetchAverageRating(productId);
        this.newReview.productId = productId; // Assign productId to newReview
      }
    });

    // Fetch the user ID from local storage
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.newReview.user.id = +userId; // Convert string to number and assign it to newReview.user.id
    }
  }

  fetchReviews(productId: number): void {
    this.reviewService.getReviewsByProductId(productId).subscribe(reviews => {
      this.reviews = reviews;
    });
  }

  getStarArray(stars: number): number[] {
    return Array(stars).fill(0);
  }

  setRating(rating: number): void {
    this.newReview.rating = rating;
  }

  fetchAverageRating(productId: number): void {
    this.reviewService.getAverageRating(productId).subscribe(rating => {
      this.averageRating = rating;
    });
  }

  submitReview(): void {
    this.reviewService.addReview(this.newReview).subscribe(review => {
      this.reviews.push(review);
      // Reset the newReview object for the next submission
      this.newReview = { rating: 0, comment: '', userName: '', userAvatar:'', user: { id: this.newReview.user.id }, productId: this.newReview.productId };
      this.fetchAverageRating(this.newReview.productId); // Refresh average rating
    });
  }
}
