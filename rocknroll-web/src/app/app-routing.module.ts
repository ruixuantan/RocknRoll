import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiceComponent } from './components/dice/dice.component';

const routes: Routes = [
  { path: 'dice', component: DiceComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
