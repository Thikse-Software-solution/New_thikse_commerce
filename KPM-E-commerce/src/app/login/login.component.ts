import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { userInfo } from 'os';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
// email: string = '';
//   password: string = '';
  loginForm: FormGroup;
  @Input() isLogin!: boolean;
  showPassword: boolean = false;
    errorMessage: string = '';


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router,
    private http: HttpClient
  ) {
   this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }
    // Toggle password visibility
    togglePasswordVisibility() {
        this.showPassword = !this.showPassword;
  }
  
onSubmit(): void {
  if (this.loginForm.valid) {
    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe({
      next: (user) => {
        // Assuming 'user' is the returned user object
        localStorage.setItem('user', JSON.stringify(user)); // Store user data
        this.router.navigate(['/shine/shinehome']); // Redirect to the home page
         Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Login successfully!',
        confirmButtonText: 'OK'
      }).then(() => {
        // Reset the form after the popup is closed
        // this.resetForm.reset();
      });
      },
 error: (errorResponse) => {
  // Handle the case when the response is plain text or not valid JSON
  if (typeof errorResponse.error === 'string') {
    this.errorMessage = errorResponse.error; // Handle as plain text
  } else if (errorResponse.error && errorResponse.error.message) {
    this.errorMessage = errorResponse.error.message; // Handle as JSON
  } else {
    this.errorMessage = 'Login failed. Please try again.';
  }
  console.error('Login error:', errorResponse); // Log the error for debugging
}
    });
  }
}








  // onSubmit(): void {
  //   if (this.loginForm.valid) {
      
  //     const { email, password } = this.loginForm.value;
  //     this.authService.login(email, password).subscribe({
  //       next: (user) => {
  //         console.log('Login successful:', user);
  //         console.log(user);
          
          
          
  //         // Handle successful login (e.g., redirect to dashboard)
  //         localStorage.setItem('user', JSON.stringify(user));
           
           
          
  //         this.router.navigate(['/shine/shinehome']);
          
  //       },
  //       error: (error) => {
  //         console.error('Login error:', error);
  //           alert("Email or password is incorrect, please check your details.");
  //         // Handle error (e.g., show an error message to the user)
  //       }
        
  //     }
  //     );
  //      this.loginForm.markAllAsTouched();
  //   this.loginForm.markAsDirty();
  //   return;
  //   }
    

    
    
//  const credentials = { email: this.email, password: this.password };

//     this.authService.login(credentials).subscribe(
//       (userDetails) => {
//         // Store user details in localStorage
//         localStorage.setItem('user', JSON.stringify(userDetails));
//         // Navigate to the profile page or dashboard
//         this.router.navigate(['/profile']);
//       },
//       error => {
//         console.error('Login error:', error);
//         // Handle login error (show error message, etc.)
//       }
//     );
  }







  

//   const { email, password } = this.loginForm.value;
//   console.log('Form Values:', { email, password }); // Debug line

//   this.authService.login(email, password).subscribe({
//     next: (response) => {
//       // Handle successful login
//       this.toastr.success('Login successful');
//       this.router.navigate(['/user']); // Redirect to the dashboard or any other page
//     },
//     error: (error) => {
//       // Handle login failure
//       console.error('Login error:', error); // Debug line
//       this.toastr.error('Login failed. Please check your credentials.');
//     }
//   });
// }
