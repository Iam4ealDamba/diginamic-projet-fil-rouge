import { Component } from '@angular/core';
import { LayoutComponent } from '../../../layout/layout.component';
import { CustomButtonLinkComponent } from '../../../components/button-link/custom-button-link.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faEnvelope,
  faEye,
  faEyeSlash,
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
import { UserType } from '../../../interfaces/types';
import { Observable } from 'rxjs';
import { AuthService } from '../../../services/auth/auth.service';
import { AuthStateReducer } from '../../../store/auth/auth.reducer';
import { ToastrService } from 'ngx-toastr';
import { Store } from '@ngrx/store';
import { authSelector } from '../../../store/auth/auth.selectors';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'user-change-password',
  standalone: true,
  imports: [
    CommonModule,
    LayoutComponent,
    CustomButtonLinkComponent,
    FontAwesomeModule,
    CustomButtonComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './user-change-password.component.html',
})
export class UserChangePasswordComponent {
  // Form Group
  form: FormGroup = new FormGroup({
    oldPassword: new FormControl('', Validators.required),
    newPassword: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required),
  });

  // Show Password
  showOldPassword: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;

  // FontAwesome Icons
  faEnvelopeIcon: IconDefinition = faEnvelope;
  faUserIcon: IconDefinition = faUser;
  faKeyIcon: IconDefinition = faKey;
  faEyeIcon: IconDefinition = faEye;
  faEyeSlashIcon: IconDefinition = faEyeSlash;

  // User details
  userData$: Observable<UserType | null> | null = null;

  constructor(
    private authService: AuthService,
    private store: Store<AuthStateReducer>,
    private toast: ToastrService
  ) {}

  ngOnInit() {
    this.userData$ = this.store.select(authSelector);
  }

  onSubmit() {
    if (this.form.valid) {
      this.userData$?.subscribe((data) => {
        if (data === null) return;

        const formData = {
          oldPassword: this.form.get('oldPassword')?.value,
          newPassword: this.form.get('newPassword')?.value,
        };

        this.authService.updatePassword(data.id, formData).subscribe(() => {
          this.toast.success(
            'Actualisation dans 3 secondes...',
            'Mot de passe mis à jour'
          );

          setTimeout(() => {
            window.location.reload();
          }, 3000);
        });
      });
    } else {
      this.toast.error(
        'Veuillez remplir tous les champs',
        'Champs non remplis'
      );
    }
  }
}
