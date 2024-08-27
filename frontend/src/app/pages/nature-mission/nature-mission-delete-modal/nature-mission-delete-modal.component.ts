import { Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-nature-mission-delete-modal',
  templateUrl: './nature-mission-delete-modal.component.html',
  styleUrls: ['./nature-mission-delete-modal.component.scss']
})
export class NatureMissionDeleteModalComponent {

  constructor(
    public dialogRef: MatDialogRef<NatureMissionDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number },
    private natureMissionService: NatureMissionService,
    private router: Router
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmDelete(): void {
    this.natureMissionService.deleteNatureMission(this.data.id).subscribe(() => {
      this.dialogRef.close(true);
    });
  }
}
