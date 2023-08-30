import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {BehaviorSubject, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {RegisterUser, User, UserLoginData} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl = environment.apiUrl;
  user = new BehaviorSubject<User | null>(null);

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  login(userData: UserLoginData): Observable<User> {
    console.log(userData)
    return this.http.post<User>(`${this.apiUrl}/login`, userData).pipe(
      tap((u) => {
        console.log(u)
        this.handleAuthentication(u)
      }),
    );
  }

  register(registerUser: RegisterUser): Observable<User> {
    console.log(registerUser)
    return this.http.post<User>(`${this.apiUrl}/register`, registerUser);
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/logowanie']);

    localStorage.removeItem('user');
  }

  autoLogin() {
    const userData: { id: number,
      username: string,
      mail: string,
      phoneNumber: string,
      password: string,
      role: string } = JSON.parse(
      localStorage.getItem('user') as string,
    );

    if (!userData) {
      return;
    }


    const user = new User(userData.id, userData.username, userData.mail, userData.phoneNumber, userData.password, userData.role);
    this.user.next(user);
  }

  private handleAuthentication(u: User) {
    if (!u) {
      return;
    }
    u.password = '';
    const user: User = u;
    this.user.next(user);
    localStorage.setItem('user', JSON.stringify(user));

    this.router.navigate(['']);
  }
}
