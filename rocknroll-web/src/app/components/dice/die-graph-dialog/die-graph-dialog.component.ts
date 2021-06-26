import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DieRow } from 'src/app/models/Dice';

@Component({
  selector: 'app-die-graph-dialog',
  templateUrl: './die-graph-dialog.component.html',
  styleUrls: ['./die-graph-dialog.component.css']
})
export class DieGraphDialogComponent {

  dieRow: DieRow;

  constructor(@Inject(MAT_DIALOG_DATA) readonly dialogDieRow: DieRow) {
    this.dieRow = dialogDieRow;
  }
}
