import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user-profile.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://192.168.1.20:8080/api/users';
  private user: User | null = null;

  constructor(private http: HttpClient) {}

  // Fetch the user profile by ID from the backend
  getUserProfileById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${userId}`);
  }

  updateProfile(userId: number, formData: FormData): Observable<any> {
    const url = `${this.apiUrl}/update/${userId}`;
    return this.http.put(url, formData, {
      headers: new HttpHeaders({
        enctype: 'multipart/form-data', // Important to let the server know it's form data
      }),
    });
  }

  // Retrieve user ID dynamically
  getUserId(): Observable<number | null> {
    const userId = localStorage.getItem('userId');
    console.log('Retrieved userId from localStorage:', userId);
    console.log(this.user);
    return of(userId ? parseInt(userId, 10) : null);
  }
  // Sign out the user
  signOut(): void {
    this.user = null;
    localStorage.removeItem('userId'); // Clear the user ID from storage if needed
    console.log('User signed out');
    // Implement any additional sign-out logic here, such as clearing tokens
  }

  // Check if the user is authenticated
  isAuthenticated(): boolean {
    return this.user !== null;
  }
}
