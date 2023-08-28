import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BorrowsComponent} from "./borrows.component";

const routes: Routes = [
  {path: 'borrows', component: BorrowsComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BorrowsRoutingModule {
}
