// app.routes.ts
import { Routes } from '@angular/router';
import { AuthGuardService } from './middlewares/auth/auth-guard.service';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { AdminMissionListComponent } from './pages/nature-mission/admin-mission-list/admin-mission-list.component';
import { NatureMissionCreateComponent } from './pages/nature-mission/nature-mission-create/nature-mission-create.component';
import { NatureMissionEditComponent } from './pages/nature-mission/nature-mission-edit/nature-mission-edit.component';
import { NatureMissionListComponent } from './pages/nature-mission/nature-mission-list/nature-mission-list.component';
import { UserChangePasswordComponent } from './pages/settings/user-change-password/user-change-password.component';
import { UserDetailsComponent } from './pages/settings/user-details/user-details.component';
export const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'naturemissions',
    component: AdminMissionListComponent,
    children: [
      {
        path: 'create',
        component: NatureMissionCreateComponent,
      },
      {
        path: 'list',
        component: NatureMissionListComponent,
      },
      {
        path: 'edit/:id',
        component: NatureMissionEditComponent,
      },
    ],
  },

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
