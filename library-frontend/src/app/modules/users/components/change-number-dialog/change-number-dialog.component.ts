import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostNumberForm, PostPasswordForm} from "../../../core/models/user.model";
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-change-number-dialog',
  templateUrl: './change-number-dialog.component.html',
  styleUrls: ['./change-number-dialog.component.css']
})
export class ChangeNumberDialogComponent  implements OnInit{
  numberForm!: FormGroup<PostNumberForm>;
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
    private dialogRef: MatDialogRef<ChangeNumberDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: number },
  ) {}

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.numberForm = new FormGroup({
      phoneNumber: new FormControl('', {nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(9),
          Validators.maxLength(9),
        ],
      }),
    });
  }

  get controls() {
    return this.numberForm.controls;
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

  onPhoneNumber() {
    let rawValues = this.numberForm.getRawValue();
    const number: string = rawValues.phoneNumber;
    this.usersService
      .changePhoneNumber(this.data.userId, number)
      .subscribe(this.observer)
  }
}
