import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostPasswordForm} from "../../../core/models/user.model";
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.css']
})
export class ChangePasswordDialogComponent implements OnInit{
  passwordForm!: FormGroup<PostPasswordForm>;
  errorMessage = '';
  observer: Observer<unknown> = {
    next: (user) => {
      console.log(user)
      this.closeDialog()
      this.errorMessage = '';
      this.router.navigate([`/users`]);
    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd';
    },
    complete: () => {},
  };

  constructor(
    private router: Router,
    private usersService: UsersService,
    private dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: number },
  ) {}

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', {nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(30),
        ],
      }),
      newPassword: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(30),
        ],
      }),
    });
  }

  get controls() {
    return this.passwordForm.controls;
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

  onChangePassword() {
    let rawValues = this.passwordForm.getRawValue();
    const oldPas: string = rawValues.oldPassword;
    const newPas: string = rawValues.newPassword;
    this.usersService
      .changePassword(this.data.userId, oldPas, newPas)
      .subscribe(this.observer)
  }
}
