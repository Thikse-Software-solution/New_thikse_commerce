export interface Order {
  orderId: number;
  amount: number;
  orderStatus: string;
  quantity: number;
  orderDate: string;
  deliveryDate: string;
  userId: number;
  addressId: number;
  productId: number;
}
