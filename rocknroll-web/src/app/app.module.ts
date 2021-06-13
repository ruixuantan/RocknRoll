import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { FormsModule } from '@angular/forms';

import { DiceComponent } from './components/dice/dice.component';
import { HeaderComponent } from './components/header/header.component';
import { CustomComponent } from './components/custom/custom.component';
import { CustomDashboardComponent } from './components/custom/custom-dashboard/custom-dashboard.component';
import { CustomFormComponent } from './components/custom/custom-form/custom-form.component';
import { DeleteAlertDialogComponent } from './components/shared/delete-alert-dialog/delete-alert-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DiceComponent,
    HeaderComponent,
    CustomComponent,
    CustomDashboardComponent,
    CustomFormComponent,
    DeleteAlertDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MaterialModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
