import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomDashboardComponent } from './custom-dashboard/custom-dashboard.component';
import { CustomComponent } from './custom.component';
import { Paths } from "../../paths";

const routes: Routes = [
  {
    path: Paths.customs, component: CustomComponent, children: [
      { path: '', component: CustomDashboardComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomRoutingModule { }
