import { AfterContentInit, Component, OnInit } from '@angular/core';
import { DieCount, DieCountSum } from 'src/app/models/Stats';
import { StatsService } from 'src/app/services/stats.service';
import * as Highcharts from 'highcharts/highcharts';
import HC_histogram from 'highcharts/modules/histogram-bellcurve';

HC_histogram(Highcharts);

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit, AfterContentInit {

  totalDieCount: DieCountSum = { total: 0 };
  topFiveDieCount: DieCount[] = [];
  dieCountDisplay: number[][] = [];

  updateFlag = false;
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};

  constructor(private statsService: StatsService) { }

  ngOnInit(): void {
    this.statsService.getTotalDieCount()
      .subscribe(
        data => this.totalDieCount = data,
        err => console.error(err)
      );
    this.statsService.getAllDieCount()
      .subscribe(
        data => this.topFiveDieCount = data,
        err => console.error(err),
        () => this.initChart(this.topFiveDieCount)
      );
  }

  ngAfterContentInit() {
    this.chartOptions.series = [{ name: '', data: this.dieCountDisplay, type: 'bar', pointWidth: 10 }];
  }

  private initChart(dieCounts: DieCount[]) {
    dieCounts.map(dieCount => this.dieCountDisplay.push([dieCount.sides, dieCount.frequency]));
    this.chartOptions.title = { text: '' };
    this.chartOptions.legend = { enabled: false };
    this.chartOptions.yAxis = { title: { text: 'Frequency' }, opposite: true };
    this.chartOptions.xAxis = { title: { text: 'Sides' } };
    this.chartOptions.tooltip = { enabled: false };
    this.updateFlag = true;
  }
}
