import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { UsersListingComponent } from './components/users-listing/users-listing.component';
import {SharedModule} from "../shared/shared.module";
import { ChangePasswordDialogComponent } from './components/change-password-dialog/change-password-dialog.component';
import { ChangeNumberDialogComponent } from './components/change-number-dialog/change-number-dialog.component';
import { ChangeAddressDialogComponent } from './components/change-address-dialog/change-address-dialog.component';
import { DeleteUserDialogComponent } from './components/delete-user-dialog/delete-user-dialog.component';
import { ChangeRoleDialogComponent } from './components/change-role-dialog/change-role-dialog.component';
import { BorrowsDetailsComponent } from './components/borrows-details/borrows-details.component';


@NgModule({
  declarations: [
    UserDetailsComponent,
    UsersListingComponent,
    ChangePasswordDialogComponent,
    ChangeNumberDialogComponent,
    ChangeAddressDialogComponent,
    DeleteUserDialogComponent,
    ChangeRoleDialogComponent,
    BorrowsDetailsComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    SharedModule
  ]
})
export class UsersModule { }
