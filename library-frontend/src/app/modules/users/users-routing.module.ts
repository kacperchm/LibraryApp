import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersListingComponent} from "./components/users-listing/users-listing.component";

const routes: Routes = [
  {path: 'users', component: UsersListingComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
