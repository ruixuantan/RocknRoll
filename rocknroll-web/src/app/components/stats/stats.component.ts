import { Component, OnInit } from '@angular/core';
import { DieCount } from 'src/app/models/Stats';
import { StatsService } from 'src/app/services/stats.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  dieCountList: DieCount[] = [];

  constructor(private statsService: StatsService) { 
    
  }

  ngOnInit(): void {
  }

  countTotalRolls() {
  }
}
