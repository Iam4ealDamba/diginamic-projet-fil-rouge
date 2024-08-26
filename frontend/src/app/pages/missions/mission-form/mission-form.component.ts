import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Mission } from '../../../models/Mission';
import { CommonModule, Location } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { TransportEnum } from '../../../enums/TransportEnum';
import { MissionService } from '../../../services/mission/mission.service';
import { NatureMission } from '../../../models/NatureMission';
import { NatureMissionService } from '../../../services/expense/nature-mission/nature-mission.service';
import { ConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';

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
    MatNativeDateModule,
    ConfirmDialogComponent
  ],
  templateUrl: './mission-form.component.html',
  styleUrls: ['./mission-form.component.scss']
})
export class MissionFormComponent {
  @Input() mission?: Mission;
  missionForm!: FormGroup;
  initialMissionData?: Mission;
  @Output() closeForm = new EventEmitter<void>();
  @Output() closeFormAndSave = new EventEmitter<Mission>();
  dialogData = {
    title: '', 
    message: 'Êtes-vous sûr de vouloir annuler vos changements ? Cette action est irréversible.',
    confirmButtonText: 'Confirmer',
    cancelButtonText: 'Annuler'
  };
  showConfirmDialog = false;

  cities = ['Paris', 'Lyon', 'Marseille', 'Nice', 'Toulouse']; //TODO: handle it dynamically ? Where ?
  transportModes = Object.keys(TransportEnum).map(key => ({
    key: key as keyof typeof TransportEnum,
    label: TransportEnum[key as keyof typeof TransportEnum]
  }));
  natureMissions : NatureMission[] = [];

  constructor(private fb: FormBuilder, private missionService: MissionService, private natureMissionService: NatureMissionService, private _location: Location) {}

  ngOnInit(): void {
    this.initializeForm();
    this.getNatureMissions();

    this.missionForm.get('natureMissionId')?.valueChanges.subscribe(natureMissionId => {
      const selectedNature = this.natureMissions.find(n => n.id === natureMissionId);
      if(selectedNature){
        this.missionForm.patchValue({labelNatureMission: selectedNature.label});
      }
    })
  }

  formHasChanges(): boolean {
    return JSON.stringify(this.missionForm.value) !== JSON.stringify(this.initialMissionData);
  }

  getNatureMissions(){
    this.natureMissionService.getNatureMissions().subscribe({
      next : (nm: NatureMission[]) => {
        this.natureMissions = nm;
      },
      error: (error) => {
        console.error(error);
      }
    })
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
      status: [this.mission?.status || 'INITIAL', Validators.required],
      startDate: [this.mission?.startDate || today, Validators.required],
      endDate: [this.mission?.endDate || tomorrow, Validators.required],
      transport: [this.mission?.transport || '', Validators.required],
      departureCity: [this.mission?.departureCity || '', Validators.required],
      arrivalCity: [this.mission?.arrivalCity || '', Validators.required],
      labelNatureMission: [this.mission?.labelNatureMission || '', Validators.required],
      userId: [this.mission?.userId || 6, Validators.required], //TODO: CRITICAL : get userId from global store
      natureMissionId: [this.mission?.natureMissionId || undefined, Validators.required],
      expense: [this.mission?.expense || null],
      bountyAmount: this.mission?.bountyAmount || 0,
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
      } else {
        this.missionService.addMission(this.missionForm.value).subscribe(
          (m: Mission) => {
            this.goBack();
          }
        ),
        (error: any) => {
          console.error(error);
        }
      }
    } else {
      console.log(this.missionForm.value);
      console.log(this.missionForm);
    }
  }

  cancel(): void {
    if (this.mission && !this.formHasChanges()) {
      this.missionForm.patchValue(this.mission);
      this.closeForm.emit();
    } else if (this.mission && this.formHasChanges()) {
      this.showDialog();
    } else {
      this.initializeForm();
      this.closeForm.emit();
    }
  }

  showDialog() {
    this.dialogData = {...this.dialogData, title:`Annuler les changements`};
    this.showConfirmDialog = true;
  } 
  cancelDialog(){
    this.showConfirmDialog = false;
  }

  cancelChanges(){
    if (this.mission && !this.formHasChanges()) {
      this.missionForm.patchValue(this.mission);
      this.closeForm.emit();
    } else {
      this.initializeForm();
      this.closeForm.emit();
    }
  }

  goBack() : void{
    this._location.back();
  }
}

//TODO:
//Handle modal confirmations action