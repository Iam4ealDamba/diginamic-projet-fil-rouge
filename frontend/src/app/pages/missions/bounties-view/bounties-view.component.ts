import { CommonModule, Location } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MissionService } from '../../../services/mission/mission.service';
import { StatCardComponent } from '../../../components/stat-card/stat-card.component';
import { BaseChartDirective } from 'ng2-charts';

import {
  Chart,
  ChartOptions,
  BarController,
  ChartData,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  registerables,
} from 'chart.js';
Chart.register(...registerables);
Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);


@Component({
  selector: 'app-bounties-view',
  standalone: true,
  imports: [CommonModule, StatCardComponent, BaseChartDirective],
  templateUrl: './bounties-view.component.html',
  styleUrl: './bounties-view.component.scss'
})
export class BountiesViewComponent {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  bountiesData: any = null;
  currentYear = new Date().getFullYear();
  barChartOptions:  ChartOptions<'bar'>  = {
    scales: {
      y: {
        beginAtZero: true
      },
      
    },
  };
  barChartLabels: string[] = [];
  
  barChartData: ChartData<'bar'> = {
    labels: [], 
    datasets: [
      {
        data: [], 
        label: 'Total primes (en euros)',
        backgroundColor: '#4d8df0',
        borderColor: '#317df4',
        borderWidth: 1,
      },
    ],
  };
  constructor(private route: ActivatedRoute, private router: Router, private missionService: MissionService, private _location: Location) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.missionService.getBounties().subscribe({
      next: (bounties: any) => {
        this.bountiesData = bounties;
        this.updateChartData(bounties.totalBountiesPerMonth);
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
      }
    });
  }

  updateChartData(totalBountiesPerMonth: { [key: string]: number }) {
    const sortedData = this.sortData(totalBountiesPerMonth);

    this.barChartLabels = Object.keys(sortedData);
    this.barChartData.labels = this.barChartLabels;
    this.barChartData.datasets[0].data = Object.values(sortedData);

    if (this.chart) {
      this.chart.update();
    }
    // console.log(this.barChartData.datasets[0].data);
  }

  sortData(totalBountiesPerMonth: { [key: string]: number }){
    const orderedMonths = [
      'Janvier',
      'Février',
      'Mars',
      'Avril',
      'Mai',
      'Juin',
      'Juillet',
      'Août',
      'Septembre',
      'Octobre',
      'Novembre',
      'Décembre'
    ];

    let orderedBounties : any = {};
    for (let i = 0; i < orderedMonths.length; i++) {
      const month = orderedMonths[i];
      orderedBounties[month.toUpperCase()] = totalBountiesPerMonth[month.toUpperCase()];
    }
    return orderedBounties;
  }
  goBack() : void{
    this._location.back();
  }
}
