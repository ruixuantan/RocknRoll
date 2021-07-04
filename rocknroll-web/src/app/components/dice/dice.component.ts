import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DieTemplate, DieRow, DieSingleResult } from 'src/app/models/Dice';
import { CustomService } from 'src/app/services/custom.service';
import { DiceService } from '../../services/dice.service';
import { DieGraphDialogComponent } from './die-graph-dialog/die-graph-dialog.component';

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css']
})
export class DiceComponent {

  dieResults: DieRow[] = [...DieTemplate];
  displayedColumns: string[] = ['input', 'output', 'expected', 'standardDeviation'];
  dieCommandInput = '';

  constructor(private diceService: DiceService, private customService: CustomService, private readonly dieGraphDialog: MatDialog) { }

  updateDieResults(input: string, output: string, expected: string, standardDeviation: string, result: DieSingleResult[]) {
    this.dieResults.unshift({ input: input, output: output, expected: expected, standardDeviation: standardDeviation, result: result });
    this.dieResults.pop();
    this.dieResults = [...this.dieResults];
  }

  parseDieInput(input: string) {
    if (!input) { return; }
    input = input.trim();
    let displayInput = input;
    try {
      const custom = this.customService.getCustom(input);
      input = custom.command;
      displayInput = custom.name;
    } catch (err) { }
    this.diceService.parseDieInput(input)
      .subscribe(
        res => this.updateDieResults(displayInput, res.resultString, res.expected, res.standardDeviation, res.results),
        err => this.updateDieResults(displayInput, err.error, '', '', [])
      );
    this.dieCommandInput = '';
  }

  clearTable() {
    this.dieResults = [...DieTemplate];
  }

  onClickDieRow(dieRow: DieRow) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = dieRow;
    if (dieRow.input !== '') {
      this.dieGraphDialog.open(DieGraphDialogComponent, dialogConfig);
    }
  }
}
