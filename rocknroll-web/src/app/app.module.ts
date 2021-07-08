import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HighchartsChartModule } from 'highcharts-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';

import { DiceComponent } from './components/dice/dice.component';
import { HeaderComponent } from './components/header/header.component';
import { CustomComponent } from './components/custom/custom.component';
import { CustomDashboardComponent } from './components/custom/custom-dashboard/custom-dashboard.component';
import { CustomFormComponent } from './components/custom/custom-form/custom-form.component';
import { DeleteAlertDialogComponent } from './components/shared/delete-alert-dialog/delete-alert-dialog.component';
import { StatsComponent } from './components/stats/stats.component';
import { DieGraphDialogComponent } from './components/dice/die-graph-dialog/die-graph-dialog.component';
import { DieGraphDisplayComponent } from './components/dice/die-graph-display/die-graph-display.component';

@NgModule({
  declarations: [
    AppComponent,
    DiceComponent,
    HeaderComponent,
    CustomComponent,
    CustomDashboardComponent,
    CustomFormComponent,
    DeleteAlertDialogComponent,
    StatsComponent,
    DieGraphDialogComponent,
    DieGraphDisplayComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    HighchartsChartModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
