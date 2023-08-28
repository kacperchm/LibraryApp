import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksListingComponent } from './components/books-listing/books-listing.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    BooksListingComponent,
    BookDetailsComponent
  ],
  imports: [
    SharedModule,
    BooksRoutingModule
  ],
  exports: [BooksListingComponent,
    BookDetailsComponent]
})
export class BooksModule { }
