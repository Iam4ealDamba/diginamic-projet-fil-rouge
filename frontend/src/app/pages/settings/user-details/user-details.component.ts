import { Component, OnInit } from '@angular/core';
import { LayoutComponent } from '../../../layout/layout.component';
import { CustomButtonLinkComponent } from '../../../components/button-link/custom-button-link.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faEnvelope,
  faKey,
  faUser,
  IconDefinition,
} from '@fortawesome/free-solid-svg-icons';
import { CustomButtonComponent } from '../../../components/buttons/custom-button.component';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import { UserType } from '../../../interfaces/types';
import { AuthStateReducer } from '../../../store/auth/auth.reducer';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { authSelector } from '../../../store/auth/auth.selectors';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [
    CommonModule,
    LayoutComponent,
    CustomButtonLinkComponent,
    FontAwesomeModule,
    CustomButtonComponent,
    ReactiveFormsModule,
  ],
  providers: [AuthService, ToastrService],
  templateUrl: './user-details.component.html',
})
export class UserDetailsComponent implements OnInit {
  // Form Group
  form: FormGroup = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    birthDate: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
  });

  // FontAwesome Icons
  faEnvelopeIcon: IconDefinition = faEnvelope;
  faUserIcon: IconDefinition = faUser;
  faKeyIcon: IconDefinition = faKey;

  // User details
  user: UserType | null = null;
  userData$: Observable<UserType | null> | null = null;

  constructor(
    private authService: AuthService,
    private store: Store<AuthStateReducer>,
    private toast: ToastrService
  ) {}

  ngOnInit() {
    this.userData$ = this.store.select(authSelector);
    this.store.select(authSelector).subscribe((data) => {
      if (data !== null) {
        this.form.patchValue({
          firstName: data?.firstName,
          lastName: data?.lastName,
          birthDate: data?.birthDate,
          email: data?.email,
        });
      }
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.userData$?.subscribe((data) => {
        if (data !== null) {
          const storeData = {
            firstName: data.firstName,
            lastName: data.lastName,
            birthDate: data.birthDate,
            email: data.email,
          };

          if (!this.compareFormValues(this.form.value, storeData)) {
            this.authService
              .update(data.id, this.form.getRawValue())
              .subscribe(() => {
                this.toast.success(
                  'Actualisation dans 3 secondes...',
                  'Changement effectué avec succès'
                );
                setTimeout(() => {
                  location.reload();
                }, 3000);
              });
          } else {
            this.toast.warning(
              "Aucun chagement n'a été éffectuée",
              'Aucun changement'
            );
          }
        }
      });
    } else {
      this.toast.error(
        'Veuillez remplir tous les champs',
        'Champs non remplis'
      );
    }
  }

  compareFormValues(form1: any, form2: any) {
    return JSON.stringify(form1) === JSON.stringify(form2);
  }
}
