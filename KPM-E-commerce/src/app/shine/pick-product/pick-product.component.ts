import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShineProductService } from '../services/shine-product.service';
import { CartService } from '../../services/cart.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
@Component({
  selector: 'app-pick-product',
  templateUrl: './pick-product.component.html',
  styleUrls: ['./pick-product.component.scss']
})
export class PickProductComponent implements OnInit {

  products: any[] = [];  // Array to hold all products
  filteredProducts: any[] = [];  // Array to hold the filtered products
  searchQuery: string = '';  // Search query for filtering
  loading = true;  // Controls the spinner visibility
    // State variable to track button text changes for added products
  isProductAdding: { [key: number]: boolean } = {}; // Tracks "Adding..." state for each product
  isProductAdded: { [key: number]: boolean } = {}; // Tracks "Added Successfully" 

  // New variables for controlling the number of visible rows
  rowsToShow: number = 2;  // Number of rows to display initially
  itemsPerRow: number = 4; // Number of products per row (depends on your layout)
  showAllProducts: boolean = false; // Flag to toggle between showing all or limited rows

  constructor(private productService: ShineProductService, private cartService: CartService,
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    private route: ActivatedRoute) { }

  ngOnInit() {
    
    this.getAllProducts();  // Fetch all products on component initialization
     // Simulate a 3-second delay for loading the spinner
     setTimeout(() => {
      this.loading = false;
    }, 3000);
  }

  // Method to fetch all products
  getAllProducts() {
    this.productService.getAllProducts().subscribe(
      (data: any[]) => {
        this.products = data;  // Assign the fetched products to the products array
        this.applyFilters();  // Apply filters after fetching products
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  // Method to apply filters based on search query
  applyFilters() {
    const query = this.searchQuery.toLowerCase().trim();

    if (query) {
      this.filteredProducts = this.products.filter(product =>
        product.name.toLowerCase().includes(query) ||
        product.price.toString().includes(query)
      );
    } else {
      this.filteredProducts = this.products;  // If no query, show all products
    }
  }

  // Toggle visibility of all products
  toggleViewMore() {
    this.showAllProducts = !this.showAllProducts;
  }

  // Determine the number of products to display
  getDisplayedProducts() {
    if (this.showAllProducts) {
      return this.filteredProducts;
    } else {
      return this.filteredProducts.slice(0, this.rowsToShow * this.itemsPerRow);
    }
  }

  // Example methods for product interactions
  buyNow(product: any) {
    this.router.navigate(['/shine/view', product.id]);
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

            // Set the button to show "Added Successfully"
            this.isProductAdding[product.id] = false; // Hide "Adding..."
            this.isProductAdded[product.id] = true; // Show "Added Successfully"

            // Reset the "Added Successfully" state after 3 seconds
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
}
