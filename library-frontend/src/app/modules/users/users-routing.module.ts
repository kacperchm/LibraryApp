import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersListingComponent} from "./components/users-listing/users-listing.component";
import {UserDetailsComponent} from "./components/user-details/user-details.component";

const routes: Routes = [
  {path: 'users', component: UsersListingComponent},
  {path: 'user-details/:id', component: UserDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
