import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PostAddressForm} from "../../../core/models/address.model";

@Component({
  selector: 'app-change-address-dialog',
  templateUrl: './change-address-dialog.component.html',
  styleUrls: ['./change-address-dialog.component.css']
})
export class ChangeAddressDialogComponent implements OnInit {
  addressForm!: FormGroup<PostAddressForm>;
  errorMessage = '';
  observer: Observer<unknown> = {
    next: (user) => {

      this.closeDialog()
      this.errorMessage = '';
      this.router.navigate([`/users`]);
    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd';
    },
    complete: () => {
    },
  };

  constructor(
    private router: Router,
    private usersService: UsersService,
    private dialogRef: MatDialogRef<ChangeAddressDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      userId: number,
      addressId: number,
      city: string,
      zipCode: string,
      street: string,
      houseNumber: string,
    },
  ) {
  }

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.addressForm = new FormGroup({
      city: new FormControl(this.data.city, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
        ],
      }),
      zipCode: new FormControl(this.data.zipCode, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(6),
        ],
      }),
      street: new FormControl(this.data.street, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
        ],
      }),
      houseNumber: new FormControl(this.data.houseNumber, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(6),
        ],
      }),
    });
  }

  get controls() {
    return this.addressForm.controls;
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

  onChangeAddress() {
    let rawValues = this.addressForm.getRawValue();
    this.usersService
      .changeAddress(
        this.data.userId,
        this.data.addressId,
        rawValues.city,
        rawValues.zipCode,
        rawValues.street,
        rawValues.houseNumber)
      .subscribe(this.observer)
  }
}
