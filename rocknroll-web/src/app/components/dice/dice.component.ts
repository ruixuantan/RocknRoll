import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import {
  DieTemplate, DieRow, DieSingleResult, Generator,
} from 'src/app/models/Dice';
import { CommandHistoryService } from 'src/app/services/command-history.service';
import { CustomService } from 'src/app/services/custom.service';
import { DiceService } from '../../services/dice.service';
import { DieGraphDialogComponent } from './die-graph-dialog/die-graph-dialog.component';

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css'],
})
export class DiceComponent {
  dieResults: DieRow[] = [...DieTemplate];

  displayedColumns: string[] = ['input', 'output', 'expected', 'standardDeviation'];

  input = '';

  generator = Generator.DEFAULT;

  generatorList = Object.values(Generator);

  constructor(
    private diceService: DiceService,
    private customService: CustomService,
    private commandHistory: CommandHistoryService,
    private readonly dieGraphDialog: MatDialog,
  ) { }

  updateDieResults(
    input: string,
    output: string,
    expected: string,
    standardDeviation: string,
    result: DieSingleResult[],
  ) {
    this.dieResults.unshift({
      input, output, expected, standardDeviation, result,
    });
    this.dieResults.pop();
    this.dieResults = [...this.dieResults];
  }

  parseDieInput() {
    if (!this.input) { return; }
    this.input = this.input.trim();
    let displayInput = this.input;
    try {
      const custom = this.customService.getCustom(this.input);
      this.input = custom.command;
      displayInput = custom.name;
    } catch (err) { }
    this.diceService.parseDieInput(this.input, this.generator)
      .subscribe(
        (res) => this.updateDieResults(
          res.inputString, res.resultString, res.expected, res.standardDeviation, res.results,
        ),
        (err) => this.updateDieResults(displayInput, err.error, '', '', []),
      );
    this.commandHistory.commit(this.input);
    this.input = '';
  }

  getPrevCommand() {
    this.input = this.commandHistory.getPrevCommand(this.input);
  }

  getNextCommand() {
    this.input = this.commandHistory.getNextCommand(this.input);
  }

  clearTable() {
    this.dieResults = [...DieTemplate];
    this.commandHistory.reset();
  }

  onClickDieRow(dieRow: DieRow) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = dieRow;
    if (dieRow.input !== '') {
      this.dieGraphDialog.open(DieGraphDialogComponent, dialogConfig);
    }
  }
}
