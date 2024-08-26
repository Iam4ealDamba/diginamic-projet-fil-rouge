import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { TableComponent } from '../../../components/table/table.component';
import { Mission } from '../../../models/Mission';
import { ActivatedRoute, Router } from '@angular/router';
import { MissionService } from '../../../services/mission/mission.service';
import { PageEvent } from '@angular/material/paginator';
import { MatFormField } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';
import { FormsModule } from '@angular/forms';

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
  selector: 'app-missions-list',
  standalone: true,
  imports: [CommonModule,FormsModule, ConfirmDialogComponent, TableComponent, MatFormField, MatSelect, MatOption],
  templateUrl: './missions-list.component.html',
  styleUrls: ['./missions-list.component.scss']
})
export class MissionsListComponent {
  headers_missions_collaborators : HeaderConfigType[] = [
    { label: 'Dates ', value: 'startDate' },
    { label: 'Libelle', value: 'label' },
    { label: 'Nature', value: 'labelNatureMission' },
    { label: 'Villes', value: 'departureCity' },
    { label: 'Transport', value: 'transport' },
    { label: 'Statut', value: 'status', isChip: true },
    { label: 'Montant prime', value: 'bountyAmount', displayCurrency: true},
  ];

  loading = true;
  responseData?: responseData;
  missions : Mission[] = [];
  respMissionsWithExpenseData? : responseData;
  showConfirmDialog = false;
  selectedMission?: Mission;
  dialogData = {
    title: '', 
    message: 'Êtes-vous sûr de vouloir supprimer cette mission ? Cette action est irréversible.',
    confirmButtonText: 'Supprimer',
    cancelButtonText: 'Annuler'
  };
 
  selectedDateSort = '';
  selectedStatus = '';
  searchType = '';
  searchQuery = '';
  selectedNatureMission = '';

  constructor(private route: ActivatedRoute, private router: Router, private missionService : MissionService, private _location: Location){}

  ngOnInit(): void {
   this.fetchData();
  }

   onFilterChange() {
    this.fetchData();
  }

  handlePageEvent(event: PageEvent) {
    this.fetchData(event);
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
