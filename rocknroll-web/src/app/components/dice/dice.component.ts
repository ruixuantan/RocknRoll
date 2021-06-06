import { Component } from '@angular/core';
import { DieContainer, DieResult } from 'src/app/models/Dice';
import { DiceService } from '../../services/dice.service';

const DIE_RESULT_TEMPLATE: DieResult[] = [
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
  { input: '', output: '' },
];

@Component({
  selector: 'app-dice',
  templateUrl: './dice.component.html',
  styleUrls: ['./dice.component.css']
})
export class DiceComponent {

  dieResults: DieResult[] = [...DIE_RESULT_TEMPLATE];
  displayedColumns: string[] = ['input', 'output'];
  dieCommandInput = '';

  constructor(private diceService: DiceService) { }

  parseDieInput(input: string) {
    if (!input) { return; }
    input = input.trim();
    this.diceService.parseDieInput(input)
      .subscribe(resMsg => {
        this.dieResults.unshift({ input: input, output: resMsg.msg });
        this.dieResults.pop();
        this.dieResults = [...this.dieResults];
      });
    this.dieCommandInput = '';
  }

  clearTable() {
    this.dieResults = [...DIE_RESULT_TEMPLATE];
  }
}
