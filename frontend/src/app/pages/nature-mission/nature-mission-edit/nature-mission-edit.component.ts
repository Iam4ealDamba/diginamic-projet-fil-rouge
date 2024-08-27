import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NatureMissionService } from '../../../services/nature-mission.service';

@Component({
  selector: 'app-nature-mission-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './nature-mission-edit.component.html',
  styleUrls: ['./nature-mission-edit.component.scss'],
})
export class NatureMissionEditComponent implements OnInit {
  form: FormGroup;

  constructor(
    private natureMissionService: NatureMissionService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = new FormGroup({
      label: new FormControl('', Validators.required),
      adr: new FormControl(0, Validators.required),
      endDate: new FormControl(new Date(), Validators.required),
      dateStart: new FormControl(new Date(), Validators.required),
      bountyRate: new FormControl(20, Validators.required),
      isBilled: new FormControl(true, Validators.required),
      isEligibleToBounty: new FormControl(false, Validators.required),
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.natureMissionService.getNatureMission(Number(id)).subscribe({
        next: (data) => {
          this.form.patchValue(data);
        },
        error: (error) => console.error('Error fetching nature mission', error)
      });
    }
  }

  saveNatureMission(): void {
    if (this.form.valid) {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) {
        this.natureMissionService.updateNatureMission(Number(id), this.form.getRawValue()).subscribe({
          next: () => this.router.navigate(['/naturemissions']),
          error: (error) => console.error('Error updating nature mission', error),
        });
      }
    } else {
      console.log('Form is invalid:', this.form.value);
    }
  }

  cancel(): void {
    this.router.navigate(['/naturemissions']);
  }
}
