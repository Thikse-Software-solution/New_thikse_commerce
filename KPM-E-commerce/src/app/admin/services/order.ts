export interface Order {
  orderId:number;
  productName: string;
  quantity: number;
  orderStatus: string;
  orderAddress:{
    name:string;
  };
  product:{
    name:string;
  }
}