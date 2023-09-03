import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from "./modules/core/core.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModule} from "./modules/auth/auth.module";
import {BooksModule} from "./modules/books/books.module";
import {BorrowsModule} from "./modules/borrows/borrows.module";
import {HomeModule} from "./modules/home/home.module";
import {UsersModule} from "./modules/users/users.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        CoreModule,
        BrowserAnimationsModule,
        AuthModule,
        BooksModule,
        BorrowsModule,
        CoreModule,
        HomeModule,
        UsersModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
