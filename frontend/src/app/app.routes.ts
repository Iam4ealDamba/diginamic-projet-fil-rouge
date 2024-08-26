import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MissionsComponent } from './pages/missions/missions.component';
import { MissionDetailsComponent } from './pages/missions/mission-details/mission-details.component';
import { NewMissionViewComponent } from './pages/missions/new-mission-view/new-mission-view.component';


export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    // pathMatch: 'full',

  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component:RegisterComponent,
  },
  {
    path: 'missions',
    component:MissionsComponent,
  },
  {
    path: 'missions/new',
    component:NewMissionViewComponent,
  },
  {
    path: 'missions/:id',
    component:MissionDetailsComponent,
  },
];
