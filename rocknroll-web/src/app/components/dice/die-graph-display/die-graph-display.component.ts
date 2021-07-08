import { Component, Input, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts/highcharts';
import { DieSingleResult } from 'src/app/models/Dice';

@Component({
  selector: 'app-die-graph-display',
  templateUrl: './die-graph-display.component.html',
  styleUrls: ['./die-graph-display.component.css'],
})
export class DieGraphDisplayComponent implements OnInit {
  @Input() dieResults!: DieSingleResult[];

  @Input() title!: string;

  updateFlag = false;

  Highcharts: typeof Highcharts = Highcharts;

  chartOptions: Highcharts.Options = {};

  ngOnInit(): void {
    this.chartOptions.series = this.dieResults
      .map((dieResult) => {
        if (dieResult.diceRolled === 1) {
          return { name: dieResult.input, data: new Array(dieResult.upperBound + 1).fill(1), type: 'column' };
        }
        return { name: dieResult.input, data: this.getPoints(dieResult), type: 'column' };
      });
    this.chartOptions.xAxis = [{
      min: Math.min(...this.dieResults.map((dieResult) => dieResult.lowerBound)),
      max: Math.max(...this.dieResults.map((dieResult) => dieResult.upperBound)),
      plotBands: this.dieResults.map((dieResult) => ({
        borderWidth: 1,
        borderColor: '#EC407A',
        color: 'transparent',
        from: dieResult.result - 0.45,
        to: dieResult.result + 0.45,
        zIndex: 10,
      })),
    }];
    this.chartOptions.yAxis = [{ visible: false }];
    this.chartOptions.tooltip = { enabled: false };
    this.chartOptions.title = { text: this.title };
    this.updateFlag = true;
  }

  // Unnecessary to multiply with the constant, as we are getting a relative shape only
  private getYCoordinate(x: number, mean: number, standardDeviation: number): number {
    return Math.exp((-0.5) * ((x - mean) / standardDeviation) ** 2);
  }

  private getPoints(dieResult: DieSingleResult): number[][] {
    return [...Array(dieResult.upperBound).keys()]
      .map((x) => [x + 1, this.getYCoordinate(x + 1, dieResult.expected, dieResult.standardDeviation)]);
  }
}
