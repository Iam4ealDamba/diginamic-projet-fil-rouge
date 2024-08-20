import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NatureMissionListComponent } from './nature-mission-list/nature-mission-list.component';
// Importez d'autres composants si nécessaire

@NgModule({
  declarations: [
    NatureMissionListComponent,
    // Déclarez d'autres composants ici
  ],
  imports: [
    CommonModule,
    // Importez d'autres modules ici
  ],
})
export class NatureMissionModule {}
