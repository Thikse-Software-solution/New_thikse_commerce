import { Component,HostListener , Inject, PLATFORM_ID, OnInit,OnDestroy,Input } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ShineProductService } from '../services/shine-product.service';
import { ProductService, Product} from '../../sheshine/services/product.service';
import { Observable } from 'rxjs';
import { User } from '../../services/user-profile.model';
import { AuthService } from '../../services/auth.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
@Component({
  selector: 'app-shine-product-view',
  templateUrl: './shine-product-view.component.html',
  styleUrls: ['./shine-product-view.component.scss']
})
export class ShineProductViewComponent implements OnInit, OnDestroy {
  // @Input() product: any;
   user: User | null = null;
    product: any;  
  isFavorite: boolean = false;
  productDetails: any;
  quantity: number = 1;
  reviewText: string = '';
  reviews: string[] = [];
  private isBrowser: boolean;
  subcategoryProducts: any[] = [];


  colors: string[] = ['#000', '#EDEDED', '#D5D6D8', '#EFE0DE', '#AB8ED1', '#F04D44'];
  activeSections: boolean[] = [false, false, false, false];
  cards: Array<{ image: string; title: string; text: string }> = [];
  duplicatedCards: Array<{ image: string; title: string; text: string }> = [];
  cardWidth = 0;
  currentIndex = 0;
  interval!: ReturnType<typeof setInterval>;
  //3d image
 threeDImages:string[] = [];
 images:string[] = [];
  isDragging = false;
   imageIndex = 0;
  startX: number = 0;
    isMobile = false;

  constructor(
    private cartService: CartService,
    @Inject(PLATFORM_ID) private platformId: Object,
    private router: Router,
    private route: ActivatedRoute,
    private shineProductService: ShineProductService,
    private productService: ProductService,
    private authService: AuthService,
    private breakpointObserver: BreakpointObserver

  ) {
    this.isBrowser = isPlatformBrowser(platformId);
   
  }


   @HostListener('window:resize', ['$event'])
  onResize(event: UIEvent) {
    this.isMobile = window.innerWidth <= 768;
  }
   isMobileView() {
    return this.isMobile;
  }

  ngOnInit(): void {
    this.isMobile = window.innerWidth <= 768;

    this.cardWidth = this.calculateCardWidth();
    if (this.isBrowser) {
      this.startCarousel();
    }

    // this.route.params.subscribe(params => {
    //   const id = +params['id']; // Get the ID from the route
    //   this.productService.getShineProducts().subscribe((products: Product[]) => {
    //     const product = products.find(p => p.id === id);
    //     if (product && product.cards) {
    //       this.cards = product.cards;
    //     }
    //   });
    // });
     // Retrieve the product details from navigation state
    const navigation = this.router.getCurrentNavigation();
    if (navigation && navigation.extras.state) {
      this.product = navigation.extras.state['product'];
    } else {
      console.error('Product details not found in navigation state');
      // Handle the case where product details are not available (e.g., redirect or show an error message)
    }


    this.route.params.subscribe(params => {
      const id = +params['id']; // Get the ID from the route
      this.productService.getShineProducts().subscribe((products: Product[]) => {
        const product = products.find(p => p.id === id);
        if (product && product.cards) {
          this.cards = product.cards;
          this.duplicateCards();
        }
      });
    });


     this.route.params.subscribe(params => {
      const id = +params['id'];
      if (id) {
        this.productService.getProductById(id).subscribe(product => {
          if (product) {
            this.product = product;
             console.log('Product from shineProductService:', this.product);
            this.threeDImages = product.threeDImages || [];
            this.images = product.images || [];
          }
        });
      }
    });
    






    this.route.params.subscribe(params => {
      const id = +params['id'];
      if (id) {
        this.shineProductService.getProductById(id).subscribe(product => {
          this.product = product;
          if (this.product) {
            if (typeof this.product.quantity !== 'number' || isNaN(this.product.quantity)) {
              this.product.quantity = 1;
            }
            if (this.product.subcategory) {
              this.fetchSubcategoryProducts(this.product.subcategory);
            }
          }
        });
      }
    });
  }

  fetchSubcategoryProducts(subcategory: string) {
    this.shineProductService.getProductsBySubcategory(subcategory).subscribe(products => {
      this.subcategoryProducts = products.filter(p => p.id !== this.product?.id); // Exclude the current product
    });
  }

  calculateCardWidth(): number {
     if (typeof window === 'undefined') {
    // Return a default value or handle the case where window is not available
    console.warn("Window object is not available.");
    return 0; // Default value
  }
    
    const screenWidth = window.innerWidth;

    if (screenWidth > 1024) {
      return screenWidth / 3; // 3 cards in view for laptop
    } else if (screenWidth >= 768) {
      return screenWidth / 2; // 2 cards in view for tablet
    } else {
      return screenWidth; // 1 card in view for mobile
    }
  }
  duplicateCards() {
    // Duplicate the cards to allow seamless scrolling
    this.duplicatedCards = [...this.cards, ...this.cards];
  }


  startCarousel() {
    this.interval = setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.cards.length;
    }, 3000);
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }




  changeImage(image: string) {
    if (this.product) {
      this.product.thumbnail = image;
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

  setRating(product: any, rating: number): void {
    if (product) {
      product.averageRating = rating;
    }
  }

  
  // Increase quantity
  increaseQuantity() {
    this.quantity++;
    this.product.quantity = this.quantity;
    console.log('Quantity increased:', this.quantity);
  }

  // Decrease quantity, ensuring it does not go below 1
  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
      this.product.quantity = this.quantity;
      console.log('Quantity decreased:', this.quantity);
    } else {
      console.log('Quantity is already at the minimum value:', this.quantity);
    }
  }

  toggleFavorite(product: any) {
    this.isFavorite = !this.isFavorite;
  }

  submitReview() {
    if (this.reviewText.trim()) {
      this.reviews.unshift(this.reviewText);
      console.log(`Review submitted: ${this.reviewText}`);
      this.reviewText = '';
    } else {
      alert('Review cannot be empty.');
    }
  }

  buyNow(id: number): void {
  if (this.product) {
    this.product.quantity = 1; // Ensure quantity is set to 1 before navigating
    console.log('Navigating to address-list with product ID:', this.product.id);
    console.log('Product quantity:', this.product.quantity);

    // Check if user is logged in
    if (this.authService.isLoggedIn()) {
      // User is logged in, proceed to address list
      this.router.navigate(['/address-list'], {
        queryParams: {
          ids: this.product.id,
          quantities: this.product.quantity
        }
      }).then(success => {
        if (success) {
          console.log('Navigation successful!');
        } else {
          console.error('Navigation failed!');
        }
      });
    } else {
      // User is not logged in, detect the device type
      const isMobile = window.innerWidth <= 768; // Adjust this value based on your mobile breakpoint

      if (isMobile) {
        // On mobile view, route directly to login page
        this.router.navigate(['/login']).then(success => {
          if (success) {
            console.log('Redirected to login page (mobile view).');
          } else {
            console.error('Navigation to login failed!');
          }
        });
      } else {
        // On laptop/tablet view, route to the toggle component (login/signup)
        this.router.navigate(['/toggle']).then(success => {
          if (success) {
            console.log('Redirected to login toggle (tablet/laptop view).');
          } else {
            console.error('Navigation to login toggle failed!');
          }
        });
      }
    }
  }
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
          console.log('Product added to cart successfully:', response);
        },
        error: (error) => {
          console.error('Error adding product to cart:', error);
        }
      });
  }


  toggleContent(index: number): void {
    this.activeSections[index] = !this.activeSections[index];
  }

  isActive(index: number): boolean {
    return this.activeSections[index];
  }

  getClass(index: number): string {
    return this.isActive(index) ? 'collapsible active' : 'collapsible';
  }

  viewProduct(subcategoryProduct: any): void {
    console.log('Product:', subcategoryProduct);
    console.log('Product ID:', subcategoryProduct.id);
    console.log('Navigating to product with ID:', subcategoryProduct.id);
    this.router.navigate(['/shine/view', subcategoryProduct.id]);
  }


  //3D image
  
  startRotation(event: MouseEvent | TouchEvent) {
    this.isDragging = true;
    this.startX = this.isTouchEvent(event) ? event.touches[0].clientX : (event as MouseEvent).clientX;
  }
  
  rotateImage(event: MouseEvent | TouchEvent) {
    if (!this.isDragging) return;

    const currentX = this.isTouchEvent(event) ? event.touches[0].clientX : (event as MouseEvent).clientX;
    const deltaX = this.startX - currentX;
    
    if (Math.abs(deltaX) > 10) {  // Adjust sensitivity
      if (deltaX > 0) {
        this.nextImage();
      } else {
        this.prevImage();
      }
      this.startX = currentX;
    }
  }
  
  stopRotation() {
    this.isDragging = false;
  }
  
  prevImage() {
    this.imageIndex = (this.imageIndex > 0) ? this.imageIndex - 1 : this.threeDImages.length - 1;
  }

  nextImage() {
    this.imageIndex = (this.imageIndex < this.threeDImages.length - 1) ? this.imageIndex + 1 : 0;
  }

  private isTouchEvent(event: MouseEvent | TouchEvent): event is TouchEvent {
    return 'touches' in event;
  }

 
 
}





