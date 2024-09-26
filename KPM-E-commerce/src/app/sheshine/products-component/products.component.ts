import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../services/product.service';
import { SharedService } from '../../services/shared.service';  
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  products: any[] = [];
  loading = true;  // Controls the spinner visibility
  filteredProducts: any[] = [];
  category: string | null = null;
  searchQuery: string = '';
  userId: number | null = null; // Ensure userId is available
 // State variable to track button text changes for added products
  isProductAdding: { [key: number]: boolean } = {}; // Tracks "Adding..." state for each product
  isProductAdded: { [key: number]: boolean } = {}; // Tracks "Added Successfully" state for each product
  constructor(
    private cartService: CartService,
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService,
    private sharedService: SharedService,
    private breakpointObserver: BreakpointObserver
  ) { }

  ngOnInit(): void {
    this.loadProducts();
    this.loadUserIdFromLocalStorage(); // Load userId from local storage

    this.route.paramMap.subscribe(params => {
      this.category = params.get('category');
      console.log('Received category:', this.category); // Debug log
      this.applyFilters();
    });
    this.sharedService.currentSearchQuery.subscribe(query => {
      this.searchQuery = query;
      this.applyFilters();
    });
     // Simulate a 3-second delay for loading the spinner
     setTimeout(() => {
      this.loading = false;
    }, 3000);
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.applyFilters();
    });
  }

  applyFilters(): void {
    const query = this.searchQuery.toLowerCase().trim();
    const price = parseFloat(query);

    this.filteredProducts = this.products.filter(product => {
      const matchesCategory = this.category ? product.category === this.category : true;
      const matchesName = product.name.toLowerCase().includes(query);
      const matchesPrice = !isNaN(price) && product.price <= price;

      return matchesCategory && (matchesName || matchesPrice);
    });

    console.log('Filtered products:', this.filteredProducts); // Debug log
  }

  toggleFavorite(product: any) {
    product.isFavorite = !product.isFavorite;
  }

  buyNow(product: any) {
    this.router.navigate(['/sheshine/view', product.id]);
  }


      addToCart(product: any): void {
    const userId = localStorage.getItem('userId');

    if (!userId) {
      console.error('User ID is not available in local storage');
      
      // Handle redirection to the login page based on screen size
      this.breakpointObserver.observe([Breakpoints.Handset]).subscribe(result => {
        if (result.matches) {
          // Route to login for mobile view
          this.router.navigate(['/login']);
        } else {
          // Route to login for tablet and laptop views
          this.router.navigate(['/toggle']);
        }
      });
      
      return;
    }

    const parsedUserId = parseInt(userId, 10);

    this.cartService.addOrUpdateCartItem(parsedUserId, product.id, product.quantity)
      .subscribe({
        next: (response) => {
            // Set the button to show "Added Successfully"
            this.isProductAdding[product.id] = false; // Hide "Adding..."
            this.isProductAdded[product.id] = true; // Show "Added Successfully"

            // Reset the "Added Successfully" state after 3 seconds
            setTimeout(() => {
              this.isProductAdded[product.id] = false;
            }, 3000); // Reset after 3 seconds
          console.log('Product added to cart successfully:', response);
        },
        error: (error) => {
          console.error('Error adding product to cart:', error);
        }
      });
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
  loadUserIdFromLocalStorage() {
    const userData = localStorage.getItem('user');
    if (userData) {
      try {
        const user = JSON.parse(userData);
        this.userId = user.id;
        console.log('User ID loaded from local storage:', this.userId);
      } catch (error) {
        console.error('Error parsing user data from local storage:', error);
      }
    } else {
      console.error('No user data found in local storage.');
    }
  }
}
