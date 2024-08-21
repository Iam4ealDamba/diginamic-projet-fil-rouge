import { Component } from '@angular/core';
import { RouterLink, RouterOutlet, RouterModule, Router, ActivatedRoute } from '@angular/router';
import { StatCardComponent } from '../../components/stat-card/stat-card.component';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { Mission } from '../../models/Mission';
import { MissionService } from '../../services/mission.service';
import { StatusEnum } from '../../enums/StatusEnum';

type HeaderConfigType = {
  label: string;
  value: keyof Mission;
  displayCurrency?: boolean;
  isChip?: boolean;
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

  missions : Mission[] = [];
  bounties : Mission[] = [];
  nbPendingMissions : number = 0;
  nbValidatedMissions : number = 0;
  nbMissionsInProgress : number = 0;
  totalBountiesAmountOfYear : number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private missionService : MissionService){}

  ngOnInit(): void {
  
    this.missionService.getMissions().subscribe({
      next: (missions : any) => {
        this.missions = missions.content;
        console.log("this.missions", this.missions);
        this.nbPendingMissions = [...this.missions].filter(m => m.status == StatusEnum.WAITING).length;
        this.nbMissionsInProgress = [...this.missions].filter(m => m.status == StatusEnum.IN_PROGRESS).length;
        this.nbValidatedMissions = [...this.missions].filter(m => m.status == StatusEnum.VALIDATED).length;
        this.totalBountiesAmountOfYear = [...this.missions]
                                  .filter(m => 
                                    new Date(m.endDate).getFullYear() === new Date().getFullYear()
                                  )
                                  .map(m => m.bountyAmount || 0).reduce((a,b) => a + b, 0); 
        this.bounties = [...this.missions].filter(m => m.bountyAmount && m.bountyAmount > 0);
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
      }
    });
  }
  
}
