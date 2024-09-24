import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user-profile.service';
import { User } from '../services/user-profile.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  editMode: boolean = false;
  profileForm: FormGroup;
  selectedAvatar: File | undefined;
  userId: number | null = null;

  constructor(private userService: UserService, private fb: FormBuilder, private router: Router) {
      this.profileForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [ Validators.email]],
     mobileNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      dob: [''],
      gender: [''],
      avatar: [null]
    });
  }

  ngOnInit(): void {
    const userString = localStorage.getItem('user');
    
    if (userString) {
      this.user = JSON.parse(userString) as User;
      this.userId = this.user.id;

      // Populate the form with user data
      this.profileForm.patchValue({
        name: this.user.name,
        email: this.user.email,
        mobileNumber: this.user.mobileNumber,
        dob: this.user.dob,
        gender: this.user.gender,
        
      });
    } else {
      console.error('User not found in localStorage');
    }
  }

 onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedAvatar = file;
    } else {
      this.selectedAvatar = undefined;
    }
  }

signOut(): void {
  this.userService.signOut();
  localStorage.removeItem('user');
  this.user = null;

  // Detect screen width to determine the device type
  const screenWidth = window.innerWidth;

  // Define breakpoints: Mobile (up to 767px), Tablet/Laptop (768px and above)
  if (screenWidth <= 767) {
    // Route to login for mobile view
    this.router.navigate(['/login']);
  } else {
    // Route to toggle for tablet/laptop view
    this.router.navigate(['/toggle']);
  }
}

  enableEdit(): void {
    this.editMode = true;
  }

  cancelEdit(): void {
    // Reset form to original data
    if (this.user) {
      this.profileForm.patchValue({
        name: this.user.name,
        email: this.user.email,
        mobileNumber: this.user.mobileNumber,
        dob: this.user.dob,
        gender: this.user.gender,
        addresses: this.user.address
      });
    }
    this.editMode = false; // Exit edit mode
  }

 updateProfile(): void {
    if (this.profileForm.valid) {
      const updatedUser = this.profileForm.value;

      // Create form data object
      const formData = new FormData();
      formData.append('user', JSON.stringify(updatedUser));
      if (this.selectedAvatar) {
        formData.append('avatar', this.selectedAvatar);
      }

         // Call service to update profile
    this.userService.updateProfile(this.userId!, formData).subscribe(
      (response) => {
        console.log('Profile updated successfully:', response);

        // Retrieve existing user data from localStorage
        const existingUser = JSON.parse(localStorage.getItem('user') || '{}');

        // Only update specific fields and keep the userId unchanged
        const updatedUserWithAvatar = {
          ...existingUser, // Spread existing properties (including userId)
          name: updatedUser.name,
          email: updatedUser.email,
          mobileNumber: updatedUser.mobileNumber,
          dob: updatedUser.dob,
          gender: updatedUser.gender,
          avatarUrl: response.avatarUrl ? response.avatarUrl : existingUser.avatarUrl, // Update avatar if provided
        };

        // Update the new user data in localStorage (keeping userId intact)
        localStorage.setItem('user', JSON.stringify(updatedUserWithAvatar));

        // Update the component's user object to reflect changes immediately in the UI
        this.user = updatedUserWithAvatar;

        // Optionally, show a success message
        // alert('Profile updated successfully');

         // Show success popup
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Profile updated successfully!',
        confirmButtonText: 'OK'
      }).then(() => {
        // Reset the form after the popup is closed
        // this.resetForm.reset();
      });

        // Optionally, display a success message
         
            this.editMode = false; // Exit edit mode
         
        },
        (error) => {
          console.error('Error updating profile:', error);
            // Show error popup if form is invalid
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Error updating profile.',
        confirmButtonText: 'OK'
      })
          // Optionally, display an error message
        }
      );
    }
  }

  closeNavbar() {
    const navbarCollapse = document.getElementById('navbarNav');
    if (navbarCollapse && navbarCollapse.classList.contains('show')) {
      navbarCollapse.classList.remove('show');
    }
  }
}
