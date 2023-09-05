import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {BorrowUserVal, PostBorrowForm} from "../../../core/models/borrow.model";
import {BorrowsService} from "../../../core/services/borrows.service";

@Component({
  selector: 'app-borrow-book-dialog',
  templateUrl: './borrow-book-dialog.component.html',
  styleUrls: ['./borrow-book-dialog.component.css']
})
export class BorrowBookDialogComponent implements OnInit {
  borrowForm!: FormGroup<PostBorrowForm>;
  errorMessage = '';
  observer: Observer<unknown> = {
    next: (user) => {
      console.log(user)
      this.closeDialog()
      this.errorMessage = '';
      this.router.navigate([`/user-details/${this.data.userId}`]);
    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd';
    },
    complete: () => {
    },
  };
  selectedValue!: string;

  borrows!: BorrowUserVal[];

  constructor(
    private router: Router,
    private usersService: UsersService,
    private borrowsService: BorrowsService,
    private dialogRef: MatDialogRef<BorrowBookDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      bookId: number,
      title: string,
      userId: number,
      isAdmin: boolean
    },
  ) {
  }

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    if (this.data.isAdmin) {
      this.usersService.getAllUsers().subscribe({
        next: (value) => {
          this.borrows = value;
        }
      })
    } else {
      this.usersService.getUser(this.data.userId).subscribe({
        next: (value) => {
          this.borrows = [value]
        }
      })
    }

    this.initForm();

  }

  private initForm() {
    this.borrowForm = new FormGroup({
      user: new FormControl(0, {
        nonNullable: true,
        validators: [
          Validators.required,
        ],
      }),
    });
  }

  get controls() {
    return this.borrowForm.controls;
  }

  getErrorMessage(control: FormControl) {
    let message = "";
    if (control.hasError('required')) {
      message = 'Musisz wpisać jakąś wartość.';
    }

    if (control.hasError('minlength') && message === "") {
      message = 'Przekazałeś za mało znaków.';
    }

    if (control.hasError('maxlength') && message === "") {
      message = 'Przekazałeś za dużo znaków.';
    }
    return message;
  }

  onBorrowBook() {
    this.borrowsService
      .borrowBook(this.data.bookId, this.data.userId)
      .subscribe(this.observer)
  }

}
