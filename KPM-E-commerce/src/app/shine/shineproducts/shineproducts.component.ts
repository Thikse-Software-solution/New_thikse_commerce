import { Component, OnInit } from '@angular/core';
import { ShineProductService } from '../services/shine-product.service';
import { CartItem, CartService } from '../../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '../../services/shared.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
@Component({
  selector: 'app-shineproducts',
  templateUrl: './shineproducts.component.html',
  styleUrls: ['./shineproducts.component.scss']
})
export class ShineproductsComponent implements OnInit {

  products: any[] = [];  // Array to hold all products
  loading = true;  // Controls the spinner visibility
  filteredProducts: any[] = [];  // Array to hold the filtered products
  userId: number = 0; // Define a variable to store userId
  // State variable to track button text changes for added products
  isProductAdding: { [key: number]: boolean } = {}; // Tracks "Adding..." state for each product
  isProductAdded: { [key: number]: boolean } = {}; // Tracks "Added Successfully" state for each product
  constructor(
    private productService: ShineProductService,
    private cartService: CartService,
    private router: Router,
    private route: ActivatedRoute,
    private sharedService: SharedService,
    private breakpointObserver: BreakpointObserver
  ) { }

  ngOnInit() {
    this.getAllProducts();
    this.loadUserId();

    // Subscribe to search query changes
    this.sharedService.currentSearchQuery.subscribe(query => {
      this.applyFilters(query);
    });
     // Simulate a 3-second delay for loading the spinner
     setTimeout(() => {
      this.loading = false;
    }, 3000);
  }

  // Method to fetch all products
  getAllProducts() {
    this.productService.getAllProducts().subscribe(
      (data: any[]) => {
        this.products = data;
        console.log(this.products)// Assign the fetched products to the products array
        this.filteredProducts = this.products;  // Initialize filteredProducts with all products
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  // Method to apply filters based on search query
  applyFilters(query: string) {
    const searchQuery = query.toLowerCase().trim();

    if (searchQuery) {
      this.filteredProducts = this.products.filter(product =>
        product.name.toLowerCase().includes(searchQuery) ||
        product.price.toString().includes(searchQuery)
      );
    } else {
      this.filteredProducts = this.products;
    }
  }

  // Example methods for product interactions
 buyNow(product: any) {
  // Navigate to the product details page with the entire product object in navigation state
  this.router.navigate(['/shine/view', product.id], {
    state: { product: product }
  });
}
  addToCart(product: any): void {
    // Ensure the product quantity is set
    product.quantity = 1;

    // Retrieve userId from local storage
    const userId = localStorage.getItem('userId');

    if (userId) {
      // Parse userId and add product to cart
      const parsedUserId = parseInt(userId, 10);

      // Call the addOrUpdateCartItem method from CartService
      this.cartService.addOrUpdateCartItem(parsedUserId, product.id, product.quantity)
        .subscribe({
          next: () => {
            console.log('Product added to cart:', product);

            // Show "Added successfully" on the button for a few seconds
              this.isProductAdding[product.id] = false; // Hide "Adding..."
            this.isProductAdded[product.id] = true;
            setTimeout(() => {
              this.isProductAdded[product.id] = false;
            }, 3000); // Reset after 3 seconds
            
            // // Navigate to the cart page with product ID and quantity in query parameters
            // this.router.navigate(['/cart'], {
            //   queryParams: { 
            //     id: product.id, 
            //     quantity: product.quantity 
            //   }
            // });
          },
          error: (error) => {
            console.error('Error adding product to cart:', error);
          }
        });
    } else {
      // If userId is not available, handle login redirection
      console.error('User ID is not available.');

      // Detect the screen size to route to the appropriate login page
      this.breakpointObserver.observe([Breakpoints.Handset]).subscribe(result => {
        if (result.matches) {
          // Route to login for mobile view
          this.router.navigate(['/login']);
        } else {
          // Route to login for tablet and laptop views
          this.router.navigate(['/toggle']);
        }
      });
    }
  }
  getStarClass(index: number, rating: number): string {
  if (rating >= index + 1) {
    return 'fa fa-star'; // filled star
  } else if (rating > index && rating < index + 1) {
    return 'fa fa-star-half-alt'; // half-filled star if you want
  } else {
    return 'fa fa-star-o'; // empty star
  }
}
  // Load userId from local storage
  private loadUserId() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        this.userId = user.id; // Extract userId from the parsed object
        console.log('User ID loaded from local storage:', this.userId);
      } catch (error) {
        console.error('Error parsing user data from local storage:', error);
      }
    } else {
      console.error('User ID not found in local storage.');
    }
  }
}
