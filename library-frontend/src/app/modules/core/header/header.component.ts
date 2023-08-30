import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {AuthService} from "../services/auth.service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  isAdmin: boolean = false;
  user: User | null = null;
  sub!: Subscription;
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.sub = this.authService.user.subscribe({
      next: (value) => {
        this.user = value;
        if(value?.role === 'ROLE_ADMIN'){
          this.isAdmin = true;
        }
      },
    });
  }

  logout() {
    this.authService.logout();
    this.isAdmin = false;
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
