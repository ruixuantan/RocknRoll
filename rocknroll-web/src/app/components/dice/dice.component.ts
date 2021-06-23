import { Component } from '@angular/core';
import { DieTemplate, DieRow } from 'src/app/models/Dice';
import { CustomService } from 'src/app/services/custom.service';
import { DiceService } from '../../services/dice.service';

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css']
})
export class DiceComponent {

  dieResults: DieRow[] = [...DieTemplate];
  displayedColumns: string[] = ['input', 'output', 'expected', 'standardDeviation'];
  dieCommandInput = '';

  constructor(private diceService: DiceService, private customService: CustomService) { }

  updateDieResults(input: string, output: string, expected: string, standardDeviation: string) {
    this.dieResults.unshift({input: input, output: output, expected: expected, standardDeviation: standardDeviation});
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
        res => this.updateDieResults(displayInput, res.results, res.expected, res.standardDeviation),
        err => this.updateDieResults(displayInput, err.error, '', '')
      );
    this.dieCommandInput = '';
  }

  clearTable() {
    this.dieResults = [...DieTemplate];
  }
}
