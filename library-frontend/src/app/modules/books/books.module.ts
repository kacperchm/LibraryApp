import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksListingComponent } from './components/books-listing/books-listing.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import {SharedModule} from "../shared/shared.module";
import { BorrowBookDialogComponent } from './components/borrow-book-dialog/borrow-book-dialog.component';


@NgModule({
  declarations: [
    BooksListingComponent,
    BookDetailsComponent,
    BorrowBookDialogComponent
  ],
  imports: [
    SharedModule,
    BooksRoutingModule
  ],
  exports: [BooksListingComponent,
    BookDetailsComponent]
})
export class BooksModule { }
