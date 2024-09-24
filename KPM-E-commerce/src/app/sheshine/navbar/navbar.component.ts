import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  menuActive = false;

  toggleMenu() {
    this.menuActive = !this.menuActive;
    console.log('Menu active:', this.menuActive); // Debug log to check toggle functionality
  }
}
