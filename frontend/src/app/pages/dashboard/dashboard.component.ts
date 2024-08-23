import { Component } from '@angular/core';
import { RouterLink, RouterOutlet, RouterModule, Router, ActivatedRoute } from '@angular/router';
import { StatCardComponent } from '../../components/stat-card/stat-card.component';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { Mission } from '../../models/Mission';
import { MissionService } from '../../services/mission/mission.service';
import { StatusEnum } from '../../enums/StatusEnum';
import { PageEvent } from '@angular/material/paginator';
import { ExpenseService } from '../../services/expense/expense.service';
import { Expense } from '../../models/Expense';

type HeaderConfigType = {
  label: string;
  value: keyof Mission;
  displayCurrency?: boolean;
  isChip?: boolean;
};

type Sort = {
  empty: boolean;
  unsorted: boolean;
  sorted: boolean;
}

type Pageable = {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  unpaged: boolean;
  paged: boolean;
}

type ExpensesData =  {
  totalPages: number;
  totalElements: number;
  size: number;
  content: Expense[];
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: Pageable;
  empty: boolean;
};

type responseData =  {
  totalPages: number;
  totalElements: number;
  size: number;
  content: Mission[];
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: Pageable;
  empty: boolean;
};
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [StatCardComponent, CommonModule, TableComponent, RouterLink, RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  
  headers_missions_collaborators : HeaderConfigType[] = [
    { label: 'Dates ', value: 'startDate' },
    { label: 'Libelle', value: 'label' },
    { label: 'Nature', value: 'labelNatureMission' },
    { label: 'Villes', value: 'departureCity' },
    { label: 'Transport', value: 'transport' },
    { label: 'Statut', value: 'status', isChip: true },
    { label: 'Montant prime', value: 'bountyAmount', displayCurrency: true},
  ];

  headers_expenses : HeaderConfigType[] = [
    { label: 'Dates ', value: 'startDate' },
    { label: 'Libelle', value: 'label' },
    { label: 'Nature', value: 'labelNatureMission' },
    { label: 'Villes', value: 'departureCity' },
    { label: 'Transport', value: 'transport' },
    { label: 'Frais', value: 'expenseId' }, //TODO: update this
  ];

  headers_bounties: HeaderConfigType[] = [
    { label: 'Nature mission', value: 'labelNatureMission' },
    { label: 'Libelle mission', value: 'label' },
    { label: 'Montant prime', value: 'bountyAmount', displayCurrency: true },
    { label: 'Dates missions', value: 'startDate' },
  ];

  dummyData = [
    {
      nom: 'Dupont',
      prenom: 'Thomas',
      signeAstro: 'Bélier',
      taille: 175,
      humeur: "Joyeux",
      poids: 70
    },
    {
      nom: 'Martin',
      prenom: 'Sophie',
      signeAstro: 'Cancer',
      taille: 160,
      humeur: "Calme",
      poids: 60
    },
    {
      nom: 'Durand',
      prenom: 'Jean',
      signeAstro: 'Vierge',
      taille: 180,
      humeur: "Sérieux",
      poids: 75
    },
    {
      nom: 'Leroy',
      prenom: 'Marie',
      signeAstro: 'Sagittaire',
      taille: 165,
      humeur: "Enthousiaste",
      poids: 55
    },
    {
      nom: 'Moreau',
      prenom: 'Pierre',
      signeAstro: 'Gémeaux',
      taille: 170,
      humeur: "Curieux",
      poids: 68
    },
    {
      nom: 'Petit',
      prenom: 'Lucie',
      signeAstro: 'Poissons',
      taille: 158,
      humeur: "Rêveuse",
      poids: 52
    },
    {
      nom: 'Rousseau',
      prenom: 'Henri',
      signeAstro: 'Scorpion',
      taille: 185,
      humeur: "Passionné",
      poids: 80
    }
  ];
 
  loading = true;
  responseData?: responseData;
  missions : Mission[] = [];
  expenses: Expense[] = [];
  expensesData? : ExpensesData;
  bounties : Mission[] = [];
  nbPendingMissions : number = 0;
  nbValidatedMissions : number = 0;
  nbMissionsInProgress : number = 0;
  totalBountiesAmountOfYear : number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private missionService : MissionService, private expenseService: ExpenseService){}

  ngOnInit(): void {
    this.loadAllMissionsForStats();
   this.fetchData();
   this.fetchExpenses();
  }

  handlePageEvent(event: PageEvent) {
    this.fetchData(event.pageIndex, event.pageSize);
  }
  handleExpenseEvent(event: PageEvent) {
    this.fetchData(event.pageIndex, event.pageSize);
  }
  
  fetchData(pageIndex = 0, pageSize = 5){
    this.missionService.getMissions(pageIndex, pageSize, undefined).subscribe({
      next: (response : any) => {
        this.responseData = response;
        this.missions = response.content;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
        this.loading = false;
      }
    });
  }

  fetchExpenses(pageIndex = 0, pageSize = 5){
    this.expenseService.getExpenses().subscribe({
      next: (res : any) => {
        this.expenses = res.content;
        this.expensesData = res;
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
        this.loading = false;
      }

    });
  }

  loadAllMissionsForStats() {
    this.missionService.getMissions(0).subscribe({
      next: (response: any) => {
        // this.allMissions = response.content;
        this.calculateStats(response.content);
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
      }
    });
  }

  calculateStats(missions : Mission[]) {
    
    this.nbPendingMissions = [...missions].filter(m => m.status == StatusEnum.WAITING).length;
    this.nbMissionsInProgress = [...missions].filter(m => m.status == StatusEnum.IN_PROGRESS).length;
    this.nbValidatedMissions = [...missions].filter(m => m.status == StatusEnum.VALIDATED).length;
    this.totalBountiesAmountOfYear = [...missions]
                              .filter(m => 
                                new Date(m.endDate).getFullYear() === new Date().getFullYear()
                              )
                              .map(m => m.bountyAmount || 0).reduce((a,b) => a + b, 0); 
    this.bounties = [...missions].filter(m => m.bountyAmount && m.bountyAmount > 0);
  }

}
