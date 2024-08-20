import { Component } from '@angular/core';
import { StatCardComponent } from '../../components/stat-card/stat-card.component';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../shared/table/table.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [StatCardComponent, CommonModule, TableComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  headers = [
    { label: 'Nom', value: 'nom' },
    { label: 'Prenom', value: 'prenom' },
    { label: 'Signe astrologique', value: 'signeAstro' },
    { label: 'Taille', value: 'taille' },
    { label: 'Humeur', value: 'humeur' },
    { label: 'Poids', value: 'poids' },
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
  
}
