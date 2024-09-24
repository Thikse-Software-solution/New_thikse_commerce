import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Address, AddressService } from '../services/address.service';
import { UserService } from '../services/user-profile.service';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.scss']
})
export class AddAddressComponent implements OnInit {
  newAddress: Address = {
    name: '',
    type: 'Home',
    phone: '',
    addressLine1: '',
    addressLine2: '',
    city: '',
    state: '',
    zip: ''
  };
  
  userId: number | null = null;
  addressId: number | null = null; // Store the address ID if editing

  productIds: string = ''; // To store the product IDs from the query params
  productQuantities: string = ''; // To store the product quantities from the query params

  constructor(
    private addressService: AddressService, 
    private router: Router,
    private userService: UserService,
    private route: ActivatedRoute  // Inject ActivatedRoute to read route parameters
  ) {}

  ngOnInit(): void {
    // Fetch the user ID dynamically
    this.userService.getUserId().subscribe(id => {
      this.userId = id;
    });

    // Check if there's an address ID in the route (for editing)
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.addressId = +params['id'];
        this.loadAddressForEdit(this.addressId);
      }
    });

    // Fetch product IDs and quantities from query params
    this.route.queryParams.subscribe(queryParams => {
      this.productIds = queryParams['ids'] || '';
      this.productQuantities = queryParams['quantities'] || '';
    });
  }

  // Load the address details for editing
  loadAddressForEdit(addressId: number): void {
    this.addressService.getAddressById(addressId).subscribe(address => {
      this.newAddress = address; // Pre-fill the form with the existing address data
    });
  }

  // Method to add or update address
  addAddress() {
    if (this.userId) {
      if (this.addressId) {
        // Update the existing address
        this.addressService.updateAddress(this.newAddress).subscribe(
          () => {
            alert('Address updated successfully');
            this.navigateToAddressList();
          },
          (error) => {
            console.error('Error updating address:', error);
          }
        );
      } else {
        // Add a new address
        this.addressService.addAddressByUserId(this.userId, this.newAddress).subscribe(
          () => {
            alert('Address added successfully');
            this.navigateToAddressList();
          },
          (error) => {
            console.error('Error adding address:', error);
          }
        );
      }
    } else {
      console.error('User ID is not available.');
    }
  }

  // Method to navigate to the address list with product data in query params
  navigateToAddressList() {
    this.router.navigate(['/address-list'], {
      queryParams: {
        ids: this.productIds, // Passing the product IDs
        quantities: this.productQuantities // Passing the product quantities
      }
    });
  }
}
