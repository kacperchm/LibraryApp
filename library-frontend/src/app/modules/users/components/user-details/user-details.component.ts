import {Component, OnInit} from '@angular/core';
import {UserDetails} from "../../../core/models/user.model";
import {UsersService} from "../../../core/services/users.service";
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  user!: UserDetails;
  id!: number;
  username!: string;
  mail!: string;
  phoneNumber!: string;
  password!: string;
  role!: string;
  addressId!: number;
  city!: string;
  zipCode!: string;
  street!: string;
  houseNumber!: string;
  memberId!: number;
  name!: string;
  surname!: string;
  numOfBorrowedBooks!: number;
  blockade!: boolean;
  myRole!: string | null;
  isAdmin!: boolean;

  constructor(
    private usersService: UsersService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.isAdmin = localStorage.getItem("user")!.includes('ROLE_ADMIN');

    this.route.params
      .pipe(switchMap((params) => this.usersService.getUser(+params['id'])))
      .subscribe({
        next: (u) => {
          this.id = u.id;
          this.username = u.username;
          this.mail = u.mail;
          this.phoneNumber = u.phoneNumber;
          this.password = u.password;
          this.role = u.role;
          this.addressId = u.addressId;
          this.city = u.city;
          this.zipCode = u.zipCode;
          this.street = u.street;
          this.houseNumber = u.houseNumber;
          this.memberId = u.memberId;
          this.name = u.name;
          this.surname = u.surname;
          this.numOfBorrowedBooks = u.numOfBorrowedBooks;
          this.blockade = u.blockade;
        },
      });
  }
}
