import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BooksListingComponent} from "./components/books-listing/books-listing.component";
import {AuthActivateGuard} from "../core/guards/auth-activate.guard";

const routes: Routes = [{path: 'books/:userId', component: BooksListingComponent, canActivate: [AuthActivateGuard]}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule {
}
