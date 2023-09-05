import {NgModule} from '@angular/core';

import {BooksRoutingModule} from './books-routing.module';
import {BooksListingComponent} from './components/books-listing/books-listing.component';
import {SharedModule} from "../shared/shared.module";
import {BorrowBookDialogComponent} from './components/borrow-book-dialog/borrow-book-dialog.component';


@NgModule({
  declarations: [
    BooksListingComponent,
    BorrowBookDialogComponent
  ],
  imports: [
    SharedModule,
    BooksRoutingModule
  ],
  exports: [BooksListingComponent]
})
export class BooksModule {
}
