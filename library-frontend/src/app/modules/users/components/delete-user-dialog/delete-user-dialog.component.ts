import {Component, Inject} from '@angular/core';
import {Observer} from "rxjs";
import {Router} from "@angular/router";
import {UsersService} from "../../../core/services/users.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-delete-user-dialog',
  templateUrl: './delete-user-dialog.component.html',
  styleUrls: ['./delete-user-dialog.component.css']
})
export class DeleteUserDialogComponent {
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
    private dialogRef: MatDialogRef<DeleteUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: number },
  ) {
  }

  closeDialog() {
    this.dialogRef.close();
  }

  onUserDelete() {
    this.usersService
      .deleteUser(this.data.userId)
      .subscribe(this.observer)
  }

}
