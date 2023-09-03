import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BorrowsRoutingModule } from './borrows-routing.module';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    BorrowsRoutingModule,
    SharedModule
  ]
})
export class BorrowsModule { }
