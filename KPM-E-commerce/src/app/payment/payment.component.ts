import { Component, OnInit, Input } from '@angular/core';
import { Address, AddressService } from '../services/address.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService, Product } from '../sheshine/services/product.service';
import { OrderService } from '../services/order.service';

import { PaymentService } from '../services/payments.service';
import { Order } from '../services/order.model';
@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  selectedAddress: Address | null = null;
  @Input() products: Product[] = [];
  totalAmount: number = 0;
  

  userId: number | null = null;
  userEmail: string | null = null;
  userName: string | null = null;
  userPhone: string | null = null;

  constructor(
    private addressService: AddressService,
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService,
    private orderService: OrderService,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const id = +params['id'];
      const ids = params['ids'];
      const quantities = params['quantities'];

      if (ids && quantities) {
        const productIds = ids.split(',').map((id: string) => +id);
        const productQuantities = quantities.split(',').map((qty: string) => +qty);

        this.loadProducts(productIds, productQuantities);
      } else if (id && params['quantity']) {
        const quantity = +params['quantity'];

        this.loadSingleProduct(id, quantity);
      }
    });

    this.addressService.getSelectedAddressObservable().subscribe(
      address => {
        this.selectedAddress = address;
        if (address) {
          this.userName = address.name;
          this.userPhone = address.phone;
        }
      },
      error => {
        console.error('Error fetching selected address:', error);
      }
    );

    this.loadUserDetailsFromLocalStorage();
  }

  private loadProducts(productIds: number[], productQuantities: number[]): void {
    const combinedProducts: Product[] = [];

    this.productService.getProducts().subscribe(products => {
      const filteredProducts = products.filter(p => productIds.includes(p.id));
      combinedProducts.push(...filteredProducts);
      this.updateQuantities(combinedProducts, productIds, productQuantities);

      this.productService.getShineProducts().subscribe(shineProducts => {
        const filteredShineProducts = shineProducts.filter(p => productIds.includes(p.id));
        combinedProducts.push(...filteredShineProducts);
        this.updateQuantities(combinedProducts, productIds, productQuantities);
        this.products = combinedProducts;
        this.calculateTotalAmount();
      });
    });
  }

  private loadSingleProduct(id: number, quantity: number): void {
    this.productService.getProducts().subscribe(products => {
      const product = products.find(p => p.id === id);
      if (product) {
        product.quantity = quantity;
        this.products.push(product);
        this.calculateTotalAmount();
      }

      this.productService.getShineProducts().subscribe(shineProducts => {
        const shineProduct = shineProducts.find(p => p.id === id);
        if (shineProduct) {
          shineProduct.quantity = quantity;
          this.products.push(shineProduct);
          this.calculateTotalAmount();
        }
      });
    });
  }

  private updateQuantities(products: Product[], productIds: number[], productQuantities: number[]): void {
    products.forEach((product) => {
      const index = productIds.indexOf(product.id);
      if (index !== -1) {
        product.quantity = productQuantities[index];
      }
    });
  }

  calculateTotalAmount(): void {
    this.totalAmount = this.products.reduce((acc, product) => acc + (product.price * (product.quantity || 1)), 0);
  }

  private loadUserDetailsFromLocalStorage(): void {
    const userData = localStorage.getItem('user');
    
    if (userData) {
      const user = JSON.parse(userData);
      this.userId = user.id;
      this.userEmail = user.email || 'no-email@example.com';
      this.userName = user.name || 'Anonymous';
      this.userPhone = user.mobileNumber || '0000000000';
      console.log('User Details from Local Storage:', {
        userId: this.userId,
        userEmail: this.userEmail,
        userName: this.userName,
        userPhone: this.userPhone
      });
    } else {
      console.error('No user data found in local storage.');
    }
  }

// onProceedToPay(): void {
//   if (this.selectedAddress && this.products.length > 0) {
//     const order = {
//       orderStatus: 'PENDING',
//       user: {
//         id: this.userId ?? 1,  // Using local storage for user ID
//       },
//       orderAddress: {
//         id: this.selectedAddress.id,  // Selected address ID
//       },
//       amount: this.totalAmount,
//       product: {
//         id: this.products[0].id,  // Assuming first product for demo, adjust as needed
//       },
//       orderDate: new Date().toISOString().split('T')[0],  // Current date
//       deliveryDate: new Date(new Date().setDate(new Date().getDate() + 7)).toISOString().split('T')[0],  // Delivery in 7 days
//       status: 'PENDING',
//     } as Order;

//     this.orderService.createOrder(order).subscribe(
//       (orderResponse: any) => {
//         const options: any = {
//           key: 'rzp_test_KicENYodOfmrEn',
//           amount: this.totalAmount * 100,  // Razorpay expects amount in paise (multiply by 100)
//           currency: 'INR',
//           name: 'Kanaga KPm',
//           description: 'Order Payment',
//           image: 'path_to_your_logo',
//           order_id: orderResponse.orderId,  // Razorpay order ID returned from the backend
//           handler: (response: any) => {
//             this.orderService.verifyPayment(response.razorpay_payment_id, response.razorpay_order_id, response.razorpay_signature)
//               .subscribe(() => {
//                 this.router.navigate(['/order']);
//               }, (error) => {
//                 console.error('Payment verification failed:', error);
//               });
//           },
//           prefill: {
//             name: this.userName || '',
//             email: this.userEmail || 'customer@example.com',
//             contact: this.userPhone || ''
//           },
//           notes: {
//             address: `${this.selectedAddress?.addressLine1}, ${this.selectedAddress?.addressLine2}`
//           },
//           theme: {
//             color: '#3399cc'
//           }
//         };

//         const rzp = new (window as any).Razorpay(options);
//         rzp.open();
//       },
//       (error) => {
//         console.error('Error creating order:', error);
//       }
//     );
//   } else {
//     console.error('Selected address or products are missing.');
//   }
// }
onProceedToPay(): void {
  if (this.selectedAddress && this.products.length > 0) {
    const addressId = this.selectedAddress.id;  // Get the selected address ID
    const userName = this.selectedAddress?.name || 'Guest';  // Fallback to 'Guest' if no name
    const userEmail = this.userEmail || 'customer@example.com';  // Use email from localStorage or provide a default
    const userPhone = this.selectedAddress?.phone || '0000000000';  // Fallback to a default number

    // Calculate delivery date (7 days from now)
    const orderDate = new Date();
    const deliveryDate = new Date(orderDate);
    deliveryDate.setDate(orderDate.getDate() + 7);

    // Iterate over each product and handle ID and quantity separately
    this.products.forEach(product => {
      const productId = product.id; // Get the individual product ID
      const quantity = product.quantity || 1;  // Get the quantity (default to 1 if undefined)

      // Create order data for each product individually
      const orderData = {
        userId: this.userId ?? 1, // Use localStorage value or fallback to 1
        productId: productId,  // Individual product ID
        quantity: quantity,  // Individual quantity
        addressId: addressId,  // Selected address ID
        amount: this.totalAmount,  // Total amount (could be modified based on the product)
        deliveryDate: deliveryDate.toISOString().split('T')[0],  // Format delivery date to 'YYYY-MM-DD'
      };

      // Call the paymentService to create a Razorpay order for each product
      this.orderService.createOrder(orderData).subscribe(orderResponse => {
        // Set up Razorpay options
        const options: any = {
          key: 'rzp_test_KicENYodOfmrEn',  // Razorpay Key ID
          amount: (this.totalAmount / this.products.length) * 100,  // Razorpay expects the amount in paise, divide by products length for simplicity
          currency: 'INR',
          name: 'Your Company Name',  // Set your company name
          description: `Payment for Product ID: ${productId}`,  // Description for the Razorpay checkout
          order_id: orderResponse.id,  // Use the order ID from your backend response (Razorpay order ID)
          handler: (response: any) => {
            // Handle the success of the payment
            console.log('Payment success for Product ID:', productId, response);
          },
          prefill: {
            name: userName,  // User's name from selected address
            email: userEmail,  // User's email from local storage or fallback
            contact: userPhone  // User's phone from selected address
          },
          notes: {
            address: `${this.selectedAddress?.addressLine1 || ''}, ${this.selectedAddress?.addressLine2 || ''}`  // Safe access for address fields
          },
          theme: {
            color: '#3399cc'  // Customize the checkout page color
          }
        };

        // Initialize Razorpay and open the payment interface for each product
        const rzp = new (window as any).Razorpay(options);
        rzp.open();
      }, error => {
        console.error(`Error in creating Razorpay order for Product ID: ${productId}`, error);
      });
    });
  } else {
    console.error('Selected address or products are missing.');
  }
}



   verifyPayment(paymentDetails: any) {
    this.orderService.verifyPayment(paymentDetails).subscribe(
      (response) => {
        console.log('Payment Verified', response);
      },
      (error) => {
        console.error('Error verifying payment', error);
      }
    );
  }



}
