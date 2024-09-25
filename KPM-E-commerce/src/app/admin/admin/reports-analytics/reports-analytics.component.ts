// src/app/reports/reports.component.ts

import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-reports',
  templateUrl: './reports-analytics.component.html',
  styleUrls: ['./reports-analytics.component.css'],
})
export class ReportsAnalyticsComponent implements OnInit {
  offerForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.offerForm = this.fb.group({
      subject: ['', Validators.required],
      message: ['', Validators.required],
    });
  }

  sendOffer() {
    if (this.offerForm.valid) {
      this.http
        .post('http://192.168.1.10:8080/api/send-offer', this.offerForm.value)
        .subscribe((response) => {
          console.log('Offer sent successfully', response);
        });
    }
  }
}
