import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { UsersListingComponent } from './components/users-listing/users-listing.component';
import { BorrowsComponent } from '../borrows/borrows.component';


@NgModule({
  declarations: [
    UserDetailsComponent,
    UsersListingComponent,
    BorrowsComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
