import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {
  resetForm: FormGroup;
  message: string = ''; // Declare `message` at the class level.

  constructor(private fb: FormBuilder, private userService: AuthService,private router: Router) {
    this.resetForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]]

    });
  }

  onSubmit() {
    if (this.resetForm.valid) {
      const { email, newPassword } = this.resetForm.value;
      this.userService.resetPassword({ email, newPassword }).subscribe({
        next: (response) => {
          this.message = response.message || 'Password reset successfully.';
        },
        error: (error) => {
          console.error('Error during password reset:', error);
          this.message = error.error || 'Password reset failed. Please try again.';
        }
      });
      // Show success popup
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Password Rest successfully!',
        confirmButtonText: 'OK'
      }).then(() => {
        // Reset the form after the popup is closed
        this.resetForm.reset();
      });
       this.router.navigate(['/shine/shinehome']);
    } else {
      
      
       // Show error popup if form is invalid
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Please fill out the form correctly.',
        confirmButtonText: 'OK'
      })
    }
  }
}