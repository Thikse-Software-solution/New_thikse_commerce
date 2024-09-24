import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private searchQuerySource = new BehaviorSubject<string>('');
  currentSearchQuery = this.searchQuerySource.asObservable();

  constructor() { }

  changeSearchQuery(query: string) {
    this.searchQuerySource.next(query);
  }
}
