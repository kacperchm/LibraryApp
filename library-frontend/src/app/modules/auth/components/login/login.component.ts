import { Component } from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {User, UserLoginData} from "../../../core/models/user.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  hide = true;
  userData: UserLoginData = {
    email: '',
    password: '',
  };

  errorMessage = '';

  constructor(private authService: AuthService) {}

  onLogin() {
    this.authService.login(this.userData).subscribe({
      next: (value) => {
        if (!value) {
          this.errorMessage = 'Podano złe dane do logowania.';
        }
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd.';
      },
    });
  }
}
