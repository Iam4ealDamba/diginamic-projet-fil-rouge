import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MissionDetailsComponent } from './pages/missions/mission-details/mission-details.component';
import { MissionsListComponent } from './pages/missions/missions-list/missions-list.component';
import { BountiesViewComponent } from './pages/missions/bounties-view/bounties-view.component';
import { AuthGuardService } from './middlewares/auth/auth-guard.service';
import { UserDetailsComponent } from './pages/settings/user-details/user-details.component';
import { UserChangePasswordComponent } from './pages/settings/user-change-password/user-change-password.component';
import { NewMissionViewComponent } from './pages/missions/new-mission-view/new-mission-view.component';
import { CollaboratorsHomeComponent } from './pages/collaborators/home/collaborators-home.component';

export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
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
    path: 'collaborators',
    component: CollaboratorsHomeComponent,
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
  {
    path: 'missions',
    component:MissionsListComponent,
  },
  {
    path: 'missions/new',
    component:NewMissionViewComponent,
  },
  {
    path: 'missions/bounties',
    component:BountiesViewComponent,
  },
  {
    path: 'missions/:id',
    component:MissionDetailsComponent,
  },
];
