// navbar.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NavbarService {
  private hideNavBar = new Subject<boolean>();

  hideNavBar$ = this.hideNavBar.asObservable();

  hide() {
    this.hideNavBar.next(true);
  }
}
