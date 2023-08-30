import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "./material/material.module";
import {HttpClientModule} from "@angular/common/http";
import {AlertComponent} from "./components/alert/alert.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [AlertComponent],
  imports: [
    CommonModule
  ],
  exports: [CommonModule,
    MaterialModule,
    HttpClientModule,
    AlertComponent,
    FormsModule,
  ReactiveFormsModule]
})
export class SharedModule { }
