export interface Review {
  id?: number;
  rating: number;
  comment: string;
  userName:string,
  userAvatar: string,
  user: {
    id: number;
  };
  productId: number; // Assuming you have a product ID in the review
}