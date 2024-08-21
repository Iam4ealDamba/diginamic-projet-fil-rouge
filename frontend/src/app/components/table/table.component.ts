// import { Component, Input } from '@angular/core';
// import {MatTableModule} from '@angular/material/table';
// import {MatPaginatorIntl, MatPaginatorModule} from '@angular/material/paginator';
// import { CommonModule } from '@angular/common';
// import { StatusEnum } from '../../enums/StatusEnum';
// import { Mission } from '../../models/Mission';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { MatMenuModule } from '@angular/material/menu';
// import { TransportEnum } from '../../enums/TransportEnum';
// import { ActivatedRoute, Router } from '@angular/router';

// type HeaderConfigType = {
//   label: string;
//   value: keyof Mission;
//   displayCurrency?: boolean;
//   isChip?: boolean;
// };
// @Component({
//   selector: 'app-table',
//   standalone: true,
//   imports: [
//     CommonModule, 
//     MatTableModule,
//     MatPaginatorModule,
//     MatMenuModule,
//     MatIconModule,
//     MatButtonModule
//   ],
//   templateUrl: './table.component.html',
//   styleUrl: './table.component.scss'
// })
// export class TableComponent {
//   @Input() headers: HeaderConfigType[] = [];
//   @Input() data: Mission[] = []; 
//   statusEnum = StatusEnum;
//   transportEnum = TransportEnum;

//   constructor(private route: ActivatedRoute, private router: Router){}

//   navigateToMission(id: number) {
//     this.router.navigate([`missions/${id}`],{relativeTo: this.route});
//   }

//   getStatusLabel(status: string): string {
//     const upperCaseStatus = status.toUpperCase() as keyof typeof StatusEnum;
//     return this.statusEnum[upperCaseStatus] || status;
//   }
//   getTransportLabel(transport: string): string {
//     const upperCaseStatus = transport.toUpperCase() as keyof typeof TransportEnum;
//     return this.transportEnum[upperCaseStatus] || transport;
//   }
  
// }


import { Component, Input, ViewChild, AfterViewInit, Injectable } from '@angular/core';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';
import { StatusEnum } from '../../enums/StatusEnum';
import { Mission } from '../../models/Mission';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { TransportEnum } from '../../enums/TransportEnum';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';

type HeaderConfigType = {
  label: string;
  value: keyof Mission;
  displayCurrency?: boolean;
  isChip?: boolean;
};

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [
    CommonModule, 
    MatTableModule,
    MatPaginatorModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule
  ],
  providers: [{provide: MatPaginatorIntl, }],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss'
})

@Injectable()
export class TableComponent implements AfterViewInit,  MatPaginatorIntl {
  @Input() headers: HeaderConfigType[] = [];
  @Input() data: Mission[] = []; 
  statusEnum = StatusEnum;
  transportEnum = TransportEnum;
  // MatTableDataSource pour gérer les données du tableau
  dataSource = new MatTableDataSource<Mission>(this.data);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private route: ActivatedRoute, private router: Router) {}
  changes: Subject<void> = new Subject<void>();
  itemsPerPageLabel: string = "Elements par page";
  nextPageLabel: string = "Suivant";
  previousPageLabel: string = "Précédent";
  firstPageLabel: string = "Premier";
  lastPageLabel: string = "Dernier";
  
  getRangeLabel(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return `Page 1 sur 1`;
    }
    const amountPages = Math.ceil(length / pageSize);
    return`Page ${page + 1} sur ${amountPages}`;
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges() {
    // Mise à jour des données du tableau lorsqu'elles changent
    this.dataSource.data = this.data;
  }

  navigateToMission(id: number) {
    this.router.navigate([`missions/${id}`], { relativeTo: this.route });
  }

  getStatusLabel(status: string): string {
    const upperCaseStatus = status.toUpperCase() as keyof typeof StatusEnum;
    return this.statusEnum[upperCaseStatus] || status;
  }

  getTransportLabel(transport: string): string {
    const upperCaseStatus = transport.toUpperCase() as keyof typeof TransportEnum;
    return this.transportEnum[upperCaseStatus] || transport;
  }
}
