import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersListingComponent} from "./components/users-listing/users-listing.component";
import {UserDetailsComponent} from "./components/user-details/user-details.component";
import {BorrowsDetailsComponent} from "./components/borrows-details/borrows-details.component";
import {AuthActivateGuard} from "../core/guards/auth-activate.guard";

const routes: Routes = [
  {path: 'users', component: UsersListingComponent, canActivate: [AuthActivateGuard]},
  {path: 'user-details/:id', component: UserDetailsComponent, canActivate: [AuthActivateGuard]},
  {path: 'user-details/:id/member/borrows/:memberId', component: BorrowsDetailsComponent, canActivate: [AuthActivateGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
