import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomRoutingModule } from './components/custom/custom-routing.module';
import { CustomComponent } from './components/custom/custom.component';
import { DiceComponent } from './components/dice/dice.component';
import {Paths} from "./paths";

const routes: Routes = [
  { path: Paths.dice, component: DiceComponent },
  { path: Paths.customs, component: CustomComponent }
];

@NgModule({
  imports: [CustomRoutingModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
