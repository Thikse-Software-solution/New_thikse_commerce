import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContactService {
  private apiUrl = 'http://192.168.1.20:8080/api/contact/submit'; // Replace with your actual backend API endpoint

  constructor(private http: HttpClient) {}

  // Method to send contact form data to the server
  submitContactForm(contactData: any): Observable<any> {
    return this.http.post(this.apiUrl, contactData);
  }
}
