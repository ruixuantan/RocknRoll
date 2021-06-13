import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Custom } from 'src/app/models/Custom';
import { CustomService } from 'src/app/services/custom.service';
import { DiceService } from 'src/app/services/dice.service';

@Component({
  selector: 'app-custom-form',
  templateUrl: './custom-form.component.html',
  styleUrls: ['./custom-form.component.css']
})
export class CustomFormComponent {
  custom: Custom;

  constructor(
    private diceService: DiceService,
    private customService: CustomService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) dialogCustom: Custom
  ) {
    this.custom = dialogCustom;
  }

  onSaveClick() {
    if (this.isNotValidCustom()) {
      return;
    }
    this.diceService.validateDieInput(this.custom.command)
      .subscribe(
        res => {
          res.isValid 
            ? this.saveCustom(res.input)
            : this.openSnackBar("Command is invalid");
        },
        err => console.error(err)
      );
  }

  openSnackBar(msg: string) {
    this.snackBar.open(msg, "Dismiss", {
      duration: 3000
    });
  }

  saveCustom(input: string) {
    this.custom.command = input;
    this.customService.storeCustom(this.custom);
    window.location.href = '/customs';
  }

  isNotValidCustom() {
    return (this.custom.name === '') || (this.custom.command === '');
  }
}
