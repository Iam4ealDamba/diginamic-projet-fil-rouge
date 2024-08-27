import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { CustomButtonComponent } from '../../../components/buttons/custom-button.component';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    CustomButtonComponent,
  ],
  providers: [RouterLink, Router, CookieService, ToastrService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  // Form Group
  registerForm = new FormGroup({
    firstname: new FormControl('', Validators.required),
    lastname: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required),
  });

  faEyeIcon = faEye;
  faEyeSlashIcon = faEyeSlash;
  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
  bgImage: string =
    'https://images.unsplash.com/photo-1521737604893-d14cc237f11d?q=80&w=2084&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D';

  constructor(
    private authService: AuthService,
    private cookie: CookieService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    if (this.cookie.check('jwt_token')) this.router.navigate(['/']);
  }

  submitRegister() {
    if (this.registerForm.valid) {
      if (
        this.registerForm.get('password')?.value ==
        this.registerForm.get('confirmPassword')?.value
      ) {
        const formData = {
          firstname: this.registerForm.get('firstname')?.value!,
          lastname: this.registerForm.get('lastname')?.value!,
          email: this.registerForm.get('email')?.value!,
          password: this.registerForm.get('password')?.value!,
        };

        this.authService.register(formData).subscribe(() => {
          this.registerForm.setValue({
            firstname: '',
            lastname: '',
            email: '',
            password: '',
            confirmPassword: '',
          });

          this.toastr.success('Votre compte a bien été crée!', 'Compte crée');
        });
      }
    } else {
      this.toastr.error('Veuillez renseigner tous les champs', 'Erreur');
    }
  }
}
