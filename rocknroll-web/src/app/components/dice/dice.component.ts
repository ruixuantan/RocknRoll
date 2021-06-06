import { Component } from '@angular/core';
import { DieTemplate, DieRow } from 'src/app/models/Dice';
import { DiceService } from '../../services/dice.service';

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css']
})
export class DiceComponent {

  dieResults: DieRow[] = [...DieTemplate];
  displayedColumns: string[] = ['input', 'output', 'expected', 'probability'];
  dieCommandInput = '';

  constructor(private diceService: DiceService) { }

  updateDieResults(input: string, output: string, expected: string, probability: string) {
    this.dieResults.unshift({input: input, output: output, expected: expected, probability: probability});
    this.dieResults.pop();
    this.dieResults = [...this.dieResults];
  }

  parseDieInput(input: string) {
    if (!input) { return; }
    input = input.trim();
    this.diceService.parseDieInput(input)
      .subscribe(
        res => this.updateDieResults(input, res.results, res.expected, res.probabilities),
        err => this.updateDieResults(input, err.error, '', '')
      );
    this.dieCommandInput = '';
  }

  clearTable() {
    this.dieResults = [...DieTemplate];
  }
}
