import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NatureMission } from '../../../interfaces/nature-mission.interface';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-nature-mission-list',
  standalone: true,
  imports: [CommonModule],
  providers: [Router],
  templateUrl: './nature-mission-list.component.html',
  styleUrls: ['./nature-mission-list.component.scss']
})
export class NatureMissionListComponent implements OnInit {
  natureMissions: NatureMission[] = [];
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;
  pages: number[] = [];

  constructor(private natureMissionService: NatureMissionService, private router: Router) {}

  ngOnInit(): void {
    this.loadMissions();
  }

  loadMissions(): void {
    this.natureMissionService.getNatureMissions().subscribe(data => {
      this.totalItems = data.length;
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
    console.log(`Editing mission with ID ${id}`);
    // Implémentation de la navigation vers le formulaire d'édition
    this.router.navigate(['/naturemissions', id]);
  }

  deleteNatureMission(id: number): void {
    this.natureMissionService.deleteNatureMission(id).subscribe(() => {
      this.natureMissions = this.natureMissions.filter(nature => nature.id !== id);
      this.totalItems--;
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((x, i) => i + 1);
      if (this.natureMissions.length === 0 && this.currentPage > 1) {
        this.currentPage--;
      }
      this.loadMissions();
    });
  }

  searchMission(event: any): void {
    const query = event.target.value.toLowerCase();
    // Implémenter la logique de recherche ici
    this.natureMissionService.getNatureMissions().subscribe(data => {
      const filteredMissions = data.filter(nature =>
        nature.label.toLowerCase().includes(query)
      );
      this.totalItems = filteredMissions.length;
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((x, i) => i + 1);
      this.natureMissions = filteredMissions.slice((this.currentPage - 1) * this.itemsPerPage, this.currentPage * this.itemsPerPage);
    });
  }

  addNewMission(): void {
    console.log('Navigating to add new mission page');
    this.router.navigate(['/naturemissions/create']);
  }
}
