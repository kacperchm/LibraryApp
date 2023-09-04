import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserDetails} from "../../../core/models/user.model";
import {UsersService} from "../../../core/services/users.service";
import {ActivatedRoute, Router} from "@angular/router";
import {map, merge, startWith, Subscription, switchMap} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {ChangePasswordDialogComponent} from "../change-password-dialog/change-password-dialog.component";
import {ChangeNumberDialogComponent} from "../change-number-dialog/change-number-dialog.component";
import {ChangeRoleDialogComponent} from "../change-role-dialog/change-role-dialog.component";
import {ChangeAddressDialogComponent} from "../change-address-dialog/change-address-dialog.component";
import {DeleteUserDialogComponent} from "../delete-user-dialog/delete-user-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {BorrowDetails} from "../../../core/models/borrow.model";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {BorrowsService} from "../../../core/services/borrows.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit, AfterViewInit {
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
  sub = new Subscription();
  // totalCount = 0;
  // dataSource!: MatTableDataSource<BorrowDetails>;
  // seeBorrows = false;
  //
  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private usersService: UsersService,
    private borrowsService: BorrowsService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private router: Router
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

/*  getBorrows() {
    this.borrowsService.getQuantityOfBorrows(this.memberId).subscribe({
      next: value => {
        this.totalCount = value;
      }
    })

    this.sub.add(
      merge(this.sort.sortChange, this.paginator.page)
        .pipe(
          startWith({}),
          switchMap(() => {
            console.log(this.memberId + "start2")
            const pageIndex = this.paginator.pageIndex;
            const itemsPerPage = this.paginator.pageSize;
            const sortDirection = this.sort.direction;
            const sortColumnName = this.sort.active;
            const mId = this.memberId;

            return this.borrowsService.getBorrowedBooks(
              mId,
              pageIndex,
              itemsPerPage,
              sortDirection,
              sortColumnName,
            );
          }),
          map((data) => {
            return data.borrows;
          }),
        )
        .subscribe((borrows) => {
          this.dataSource = new MatTableDataSource<BorrowDetails>(borrows);
        }),
    );

    this.seeBorrows = true;

  }*/

  openChangePassword() {
    const dialogRef = this.dialog.open(ChangePasswordDialogComponent, {
      data: {
        userId: this.id,
      },
      width: '600px',
      maxWidth: '600px',
    });
  }

  openChangePhoneNumber() {
    const dialogRef = this.dialog.open(ChangeNumberDialogComponent, {
      data: {
        userId: this.id,
      },
      width: '600px',
      maxWidth: '600px',
    });
  }

  changeRole() {
    const dialogRef = this.dialog.open(ChangeRoleDialogComponent, {
      data: {
        userId: this.id,
      },
      width: '600px',
      maxWidth: '600px',
    });
  }

  openChangeAddress() {
    const dialogRef = this.dialog.open(ChangeAddressDialogComponent, {
      data: {
        userId: this.id,
        addressId: this.addressId,
        city: this.city,
        zipCode: this.zipCode,
        street: this.street,
        houseNumber: this.houseNumber,
      },
      width: '600px',
      maxWidth: '600px',
    });
  }

  deleteUser() {
    const dialogRef = this.dialog.open(DeleteUserDialogComponent, {
      data: {
        userId: this.id,
      },
      width: '600px',
      maxWidth: '600px',
    });
  }

  ngAfterViewInit(): void {

  }
}
