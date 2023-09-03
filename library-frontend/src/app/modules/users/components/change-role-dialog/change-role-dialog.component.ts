import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostPasswordForm, PostRoleForm, Role} from "../../../core/models/user.model";
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-change-role-dialog',
  templateUrl: './change-role-dialog.component.html',
  styleUrls: ['./change-role-dialog.component.css']
})
export class ChangeRoleDialogComponent implements OnInit{
  roleForm!: FormGroup<PostRoleForm>;
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
  selectedValue!: string;

  roles: Role[] = [
    {value: 'ROLE_ADMIN', viewValue: 'Administrator'},
    {value: 'ROLE_USER', viewValue: 'Użytkownik'},
  ];

  constructor(
    private router: Router,
    private usersService: UsersService,
    private dialogRef: MatDialogRef<ChangeRoleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: number },
  ) {}

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.roleForm = new FormGroup({
      role: new FormControl('', {nonNullable: true,
        validators: [
          Validators.required,
        ],
      }),
    });
  }

  get controls() {
    return this.roleForm.controls;
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

  onChangeRole() {
    let rawValues = this.roleForm.getRawValue();
    const newRole: string = rawValues.role;
    console.log(newRole)
    this.usersService
      .changeRole(this.data.userId, newRole)
      .subscribe(this.observer)
  }
}
