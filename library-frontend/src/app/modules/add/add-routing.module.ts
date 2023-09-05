import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddComponent} from "./add.component";
import {AuthActivateGuard} from "../core/guards/auth-activate.guard";

const routes: Routes = [{path: 'add/book', component: AddComponent, canActivate:[AuthActivateGuard]}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddRoutingModule {
}
