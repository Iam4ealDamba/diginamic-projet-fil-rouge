import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Mission } from '../../../models/Mission';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { TransportEnum } from '../../../enums/TransportEnum';
import { MissionService } from '../../../services/mission/mission.service';

@Component({
  selector: 'app-mission-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './mission-form.component.html',
  styleUrls: ['./mission-form.component.scss']
})
export class MissionFormComponent {
  @Input() mission?: Mission;
  missionForm!: FormGroup;
  @Output() closeForm = new EventEmitter<void>();
  @Output() closeFormAndSave = new EventEmitter<Mission>();

  cities = ['Paris', 'Lyon', 'Marseille', 'Nice', 'Toulouse']; 
  // transports = TransportEnum;

  constructor(private fb: FormBuilder, private missionService: MissionService) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mission'] && this.missionForm) {
      this.missionForm.patchValue(this.mission || {});
    }
  }

  private initializeForm(): void {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);

    this.missionForm = this.fb.group({
      ...(this.mission && {id: this.mission.id}),
      label: [this.mission?.label || '', Validators.required],
      totalPrice: [this.mission?.totalPrice || 0, Validators.required],
      status: [this.mission?.status || '', Validators.required],
      startDate: [this.mission?.startDate || today, Validators.required],
      endDate: [this.mission?.endDate || tomorrow, Validators.required],
      transport: [this.mission?.transport || '', Validators.required],
      departureCity: [this.mission?.departureCity || '', Validators.required],
      arrivalCity: [this.mission?.arrivalCity || '', Validators.required],
      labelNatureMission: [this.mission?.labelNatureMission || '', Validators.required],
      userId: [this.mission?.userId || 0, Validators.required],
      natureMissionId: [this.mission?.natureMissionId || 0, Validators.required],
      expenseId: [this.mission?.expenseId || undefined]
    });

    if (this.mission) {
      this.missionForm.patchValue(this.mission);
    } 
  }

  submit(): void {
    if (this.missionForm.valid) {
      const missionData = this.missionForm.value;
      
      if(this.mission) {
        this.missionService.updateMission(missionData).subscribe(
          (m: Mission) => {
            console.log("mission saved", missionData);
            this.closeFormAndSave.emit(missionData);
          }
        ),
        (error: any) => {
          console.error(error);
        }

      }
    }
  }

  cancel(): void {
    if(this.mission){
      this.missionForm.patchValue(this.mission);
    } else {
      this.initializeForm();
    }
    this.closeForm.emit();
    
  }
}

//TODO:
//Add missing fields
//Handle modal confirmations action