import { Component } from '@angular/core';
import { RouterLink, RouterOutlet, RouterModule, Router, ActivatedRoute } from '@angular/router';
import { StatCardComponent } from '../../components/stat-card/stat-card.component';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { Mission } from '../../models/Mission';
import { MissionService } from '../../services/mission.service';
import { StatusEnum } from '../../enums/StatusEnum';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [StatCardComponent, CommonModule, TableComponent, RouterLink, RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  
  headers_missions_collaborators = [
    { label: 'Dates ', value: 'startDate' },
    { label: 'Libelle', value: 'label' },
    { label: 'Nature', value: 'labelNatureMission' },
    { label: 'Villes', value: 'departureCity' },
    { label: 'Transport', value: 'transport' },
    { label: 'Statut', value: 'status' },
    { label: 'Montant prime', value: 'bountyAmount' },
    { label: 'Actions', value: 'actions' },
  ];
  headers_expenses= [
    { label: 'Dates ', value: 'startDate' },
    { label: 'Libelle', value: 'label' },
    { label: 'Nature', value: 'labelNatureMission' },
    { label: 'Villes', value: 'departureCity' },
    { label: 'Transport', value: 'transport' },
    { label: 'Frais', value: 'expenseId' }, //TODO: update this
    { label: 'Actions', value: 'actions' },
  ];
  headers_bounties = [
    { label: 'Nature mission', value: 'labelNatureMission' },
    { label: 'Libelle mission', value: 'label' },
    { label: 'Montant prime', value: 'bountyAmount' },
    { label: 'Dates missions', value: 'startDate' },
    { label: 'Actions', value: 'actions' },
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
  nbPendingMissions : number = 0;
  nbValidatedMissions : number = 0;
  nbMissionsInProgress : number = 0;
  totalBountiesAmount : number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private missionService : MissionService){}

  ngOnInit(): void {
  
    this.missionService.getMissions().subscribe({
      next: (missions : any) => {
        this.missions = missions.content;
        console.log("this.missions", this.missions);
        this.nbPendingMissions = [...this.missions].filter(m => m.status == StatusEnum.WAITING).length;
        this.nbMissionsInProgress = [...this.missions].filter(m => m.status == StatusEnum.IN_PROGRESS).length;
        this.nbValidatedMissions = [...this.missions].filter(m => m.status == StatusEnum.VALIDATED).length;
        this.totalBountiesAmount = this.missions.map(m => m.bountyAmount || 0).reduce((a,b) => a + b, 0); //TODO: Filtrer primes de cette année
        
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(['/404']);
      }
    });
  }
  
}
