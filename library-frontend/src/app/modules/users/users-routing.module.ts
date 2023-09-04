import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersListingComponent} from "./components/users-listing/users-listing.component";
import {UserDetailsComponent} from "./components/user-details/user-details.component";
import {BorrowsDetailsComponent} from "./components/borrows-details/borrows-details.component";

const routes: Routes = [
  {path: 'users', component: UsersListingComponent},
  {path: 'user-details/:id', component: UserDetailsComponent},
  {path: 'user-details/:id/member/borrows/:memberId', component: BorrowsDetailsComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
