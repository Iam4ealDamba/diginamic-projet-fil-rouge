import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NatureMission } from '../../../interfaces/nature-mission.interface';
import { LayoutComponent } from '../../../layout/layout.component';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-admin-mission-list',
  standalone: true,
  imports: [CommonModule, LayoutComponent],
  providers: [Router],
  templateUrl: './admin-mission-list.component.html',
  styleUrls: ['./admin-mission-list.component.scss'],

})
export class AdminMissionListComponent implements OnInit {
  natureMissions: NatureMission[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalItems: number = 0;
  pages: number[] = [];

  constructor(
    private natureMissionService: NatureMissionService,
    private router: Router
  ) {}

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

  onAddNew(): void {
    this.router.navigate(['/naturemissions/create']);
  }

  onEdit(id: number): void {
    this.router.navigate(['/url pour edit', id]);// il faut rajouter une page avec la mission déja excitee pour la modifier 
  }

  onDelete(id: number): void {
    this.natureMissionService.deleteNatureMission(id).subscribe(() => {
      this.natureMissions = this.natureMissions.filter(mission => mission.id !== id);
      this.totalItems--;
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((_, i) => i + 1);
      if (this.natureMissions.length === 0 && this.currentPage > 1) {
        this.currentPage--;
      }
      this.loadMissions();
    });
  }

  searchMission(event: any): void {
    const query = event.target.value.toLowerCase();
    this.natureMissionService.getNatureMissions().subscribe(data => {
      const filteredMissions = data.filter(mission =>
        mission.label.toLowerCase().includes(query)
      );
      this.totalItems = filteredMissions.length;
      this.pages = Array(Math.ceil(this.totalItems / this.itemsPerPage)).fill(0).map((_, i) => i + 1);
      this.natureMissions = filteredMissions.slice((this.currentPage - 1) * this.itemsPerPage, this.currentPage * this.itemsPerPage);
    });
  }
}
