export interface Order {
  id: number;
  userId: number;
  amount: number;
  status: string;
  items: any[];  // Define based on the structure of the items in the order
  createdAt: Date;
  updatedAt: Date;
}
