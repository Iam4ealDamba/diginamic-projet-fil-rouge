import { Component, OnInit } from '@angular/core';
import { NatureMission } from '../../../interfaces/nature-mission.interface';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-nature-mission-list',
  templateUrl: './nature-mission-list.component.html',
  styleUrls: ['./nature-mission-list.component.scss']
})
export class NatureMissionListComponent implements OnInit {
  natureMissions: NatureMission[] = [];
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0; // Initialisé à 0, sera mis à jour après récupération des données
  pages: number[] = []; // Tableau pour stocker les numéros de pages

  constructor(private natureMissionService: NatureMissionService) {}

  ngOnInit(): void {
    this.loadMissions();
  }

  loadMissions(): void {
    this.natureMissionService.getNatureMissions().subscribe(data => {
      this.totalItems = data.length; // Mise à jour du nombre total d'éléments
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((x, i) => i + 1);
      this.natureMissions = data.slice((this.currentPage - 1) * this.itemsPerPage, this.currentPage * this.itemsPerPage);
    });
  }

  goToPage(page: number): void {
    this.currentPage = page;
    this.loadMissions();
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadMissions();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.pages.length) {
      this.currentPage++;
      this.loadMissions();
    }
  }

  editNatureMission(id: number): void {
    // Logique pour modifier la mission, par exemple navigation vers une page d'édition
    console.log(`Editing mission with ID ${id}`);
  }

  deleteNatureMission(id: number): void {
    this.natureMissionService.deleteNatureMission(id).subscribe(() => {
      // Mise à jour de la liste des missions après suppression
      this.natureMissions = this.natureMissions.filter(nature => nature.id !== id);
      this.totalItems--; // Mise à jour du nombre total d'éléments
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((x, i) => i + 1);

      // Recharger la page courante pour tenir compte des changements
      if (this.natureMissions.length === 0 && this.currentPage > 1) {
        this.currentPage--;
      }
      this.loadMissions();
    });
  }
}
