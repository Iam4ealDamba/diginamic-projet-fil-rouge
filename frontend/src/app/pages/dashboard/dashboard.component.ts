import { Component } from '@angular/core';
import { RouterLink, RouterOutlet, RouterModule, Router, ActivatedRoute } from '@angular/router';
import { StatCardComponent } from '../../components/stat-card/stat-card.component';
import { CommonModule, CurrencyPipe, Location } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { Mission } from '../../models/Mission';
import { MissionService } from '../../services/mission/mission.service';
import { StatusEnum } from '../../enums/StatusEnum';
import { PageEvent } from '@angular/material/paginator';
import { ExpenseService } from '../../services/expense/expense.service';
import { Expense } from '../../models/Expense';
import { ConfirmDialogComponent } from '../../components/confirm-dialog/confirm-dialog.component';

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
  imports: [StatCardComponent, CommonModule, TableComponent, RouterLink, RouterOutlet, ConfirmDialogComponent],
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
    { label: 'Frais', value: 'expense' },
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
  missionsWithExpenses: Mission[] = [];
  expensesData? : ExpensesData;
  respMissionsWithExpenseData? : responseData;
  bounties : Mission[] = [];
  nbPendingMissions : number = 0;
  nbValidatedMissions : number = 0;
  nbMissionsInProgress : number = 0;
  totalBountiesAmountOfYear : number = 0;
  showConfirmDialog = false;
  selectedMission?: Mission;
  dialogData = {
    title: '', 
    message: 'Êtes-vous sûr de vouloir supprimer cette mission ? Cette action est irréversible.',
    confirmButtonText: 'Supprimer',
    cancelButtonText: 'Annuler'
  };
  


  constructor(private route: ActivatedRoute, private router: Router, private missionService : MissionService, private expenseService: ExpenseService, private _location: Location){}

  ngOnInit(): void {
    this.loadAllMissionsForStats();
   this.fetchData();
   this.fetchMissionsWithExpenses();
  }

  handlePageEvent(event: PageEvent) {
    this.fetchData(event);
  }
  handleMissionsWithExpenses(event: PageEvent) {
    this.fetchMissionsWithExpenses(event);
  }
  
  fetchData(event : PageEvent | null = null){
    const queries = {
      page : event?.pageIndex || 0,
      size : event?.pageSize || 5,
      searchbar: undefined,
    };
    this.missionService.getMissions(queries).subscribe({
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

  fetchMissionsWithExpenses(event : PageEvent | null = null){
    const queries = {
      page : event?.pageIndex || 0,
      size : event?.pageSize || 5,
      searchbar: undefined,
      withExpense: true,
    };
    this.missionService.getMissions(queries).subscribe({
      next: (response : any) => {
        // console.log("missions w/ exp: ", response);
        this.respMissionsWithExpenseData = response;
        this.missionsWithExpenses = response.content;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
        this.loading = false;
      }
    });
  }

  //TODO: fetch missions with bountyAmount (non-paginated)
  loadAllMissionsForStats() {
    const queries = {
      page : 0,
      size : undefined,
      searchbar: undefined,
    };
    this.missionService.getMissions(queries).subscribe({
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
    this.nbPendingMissions = [...missions].filter(m => m.status.toUpperCase() === "WAITING").length;

    this.nbMissionsInProgress = [...missions].filter(m => m.status.toUpperCase() === "IN_PROGRESS").length;
    this.nbValidatedMissions = [...missions].filter(m => m.status.toUpperCase() === "VALIDATED").length;
    this.totalBountiesAmountOfYear = [...missions]
                              .filter(m => 
                                new Date(m.endDate).getFullYear() === new Date().getFullYear()
                              )
                              .map(m => m.bountyAmount || 0).reduce((a,b) => a + b, 0); 
    this.bounties = [...missions].filter(m => m.bountyAmount && m.bountyAmount > 0);
  }

  deleteMission(){
    if (this.selectedMission) {
      this.missionService.deleteMission(this.selectedMission.id.toString()).subscribe({
        next: () => {
          this.fetchData();
          this.showConfirmDialog = false;

  
        },
        error: (error) => {
          console.error(error);
          this.router.navigate(["/404"]);
        }
      })    
    }
  }

  cancelDialog(){
    this.selectedMission = undefined;
    this.showConfirmDialog = false;
  }

  goBack() : void{
    this._location.back();
  }

  showDialog(mission: Mission) {
    this.selectedMission = mission;
    this.dialogData = {...this.dialogData, title:`Supprimer la mission #${mission.id}`};
    this.showConfirmDialog = true;
  }  

}
