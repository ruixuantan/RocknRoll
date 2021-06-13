import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Custom } from 'src/app/models/Custom';
import { CustomService } from 'src/app/services/custom.service';

@Component({
  selector: 'app-custom-form',
  templateUrl: './custom-form.component.html',
  styleUrls: ['./custom-form.component.css']
})
export class CustomFormComponent {
  custom: Custom;

  constructor(
    private customService: CustomService,
    @Inject(MAT_DIALOG_DATA) dialogCustom: Custom
  ) {
    this.custom = dialogCustom;
  }

  onSaveClick() {
    this.customService.storeCustom(this.custom);
    window.location.href = '/customs';
  }
}
