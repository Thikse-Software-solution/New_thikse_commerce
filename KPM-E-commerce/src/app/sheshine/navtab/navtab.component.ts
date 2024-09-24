import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { SharedService } from '../../services/shared.service';
import { ActivatedRoute } from '@angular/router';
import { User } from '../../services/user-profile.model';
import { UserService } from '../../services/user-profile.service';

@Component({
  selector: 'app-navtab',
  templateUrl: './navtab.component.html',
  styleUrls: ['./navtab.component.scss'] // Corrected 'styleUrl' to 'styleUrls'
})
export class NavtabComponent implements OnInit {
  searchQuery: string = '';
  cartItemCount: number = 0;
  showNavbar: boolean = true;
  showKpnRunner: boolean = true;
  userId: number | null = null;
  user: User | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cartService: CartService,
    private sharedService: SharedService,
    private userService: UserService
    
  ) {}

  ngOnInit(): void {
   const userString = localStorage.getItem('user');
if (userString) {
  this.user = JSON.parse(userString) as User;
  console.log(this.user.avatarUrl);  // Check if this is a valid base64 string
  console.log(this.user.id);  // Check if this is a valid base64 string
} else {
  console.error('User not found in localStorage');
}



    // Listen to router events
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const hiddenRoutes = ['/address', '/payment', '/cart', '/login', '/signup', '/toggle'];
        this.showNavbar = !hiddenRoutes.some(route => event.url.includes(route));
        this.showKpnRunner = !event.url.includes('/toggle');
      }
    });
  }

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


  isRouteActive(route: string): boolean {
    return this.router.url === route;
  }

  onProduct(): void {
    const currentRoute = this.router.url.split('?')[0];
    console.log('Current route:', currentRoute);

    if (currentRoute === '/shine/shinehome') {
      console.log('Navigating to products page with search:', this.searchQuery);
      this.router.navigate(['/shine/shineproducts'], { queryParams: { search: this.searchQuery } });
    } else if (currentRoute === '/sheshine/home') {
      console.log('Navigating to products page with search:', this.searchQuery);
      this.router.navigate(['/sheshine/products'], { queryParams: { search: this.searchQuery } });
    } else {
      console.log('Applying search within the current page:', this.searchQuery);
      this.router.navigate([], {
        queryParams: { search: this.searchQuery },
        relativeTo: this.route
      });
    }
  }

  onSearchChange() {
    this.sharedService.changeSearchQuery(this.searchQuery);
  }

  isLoggedIn(): boolean {
    const user = localStorage.getItem('user');
    return !!user;
  }
}
