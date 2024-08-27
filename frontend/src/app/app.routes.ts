// app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuardService } from './middlewares/auth/auth-guard.service';
import { UserDetailsComponent } from './pages/settings/user-details/user-details.component';
import { UserChangePasswordComponent } from './pages/settings/user-change-password/user-change-password.component';

export const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'naturemissions-create', component: NatureMissionCreateComponent},
  { path: 'naturemissions-create/list', component: NatureMissionListComponent },
  { path: 'admin/naturemissions', component: AdminMissionListComponent },
  { path: '**', redirectTo: 'admin/naturemissions' },
  { path: 'naturemissions-edit/:id', component: NatureMissionEditComponent }
  

  
 // { path: 'dashboard', component: DashboardComponent },
 //  { path: 'missions', component: MissionsComponent },
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full',
    canActivate: [AuthGuardService],
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'settings',
    children: [
      {
        path: 'user-details',
        component: UserDetailsComponent,
      },
      {
        path: 'change-password',
        component: UserChangePasswordComponent,
      },
    ],
  },
];
