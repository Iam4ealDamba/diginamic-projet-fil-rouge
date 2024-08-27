import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-nature-mission-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './nature-mission-create.component.html',
  styleUrls: ['./nature-mission-create.component.scss'],
})
export class NatureMissionCreateComponent {
  form: FormGroup = new FormGroup({
    label: new FormControl('', Validators.required),
    adr: new FormControl(0, Validators.required), // "adr" est correct ici si c'est bien ce que vous voulez
    endDate: new FormControl(new Date(), Validators.required),
    startDate: new FormControl(new Date(), Validators.required),
    bountyRate: new FormControl(20, Validators.required),
    isBilled: new FormControl(true, Validators.required),
    isEligibleToBounty: new FormControl(false, Validators.required),
  });

  constructor(
    private natureMissionService: NatureMissionService,
    private router: Router
  ) {}

  createNatureMission(): void {
    if (this.form.valid) {
      this.natureMissionService.createNatureMission(this.form.value).subscribe({
        next: () => {
          console.log('formulaire', this.form.value);
          // Après la création, naviguer vers la liste des missions
          this.router.navigate(['/naturemissions-create/list']);
        },
        error: (error) => console.error('Error creating nature mission', error),
      });
    } else {
      console.log('Form is invalid:', this.form.value);
    }
  }
  

  cancel(): void {
    this.router.navigate(['/naturemissions']);
  }
}
