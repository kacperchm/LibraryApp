import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BooksListingComponent} from "./components/books-listing/books-listing.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";

const routes: Routes = [{ path: 'books', component: BooksListingComponent },
  { path: 'book-details/:id', component: BookDetailsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule { }
