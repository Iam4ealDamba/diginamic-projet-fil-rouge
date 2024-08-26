import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MissionDetailsComponent } from './pages/missions/mission-details/mission-details.component';
import { NewMissionViewComponent } from './pages/missions/new-mission-view/new-mission-view.component';
import { MissionsListComponent } from './pages/missions/missions-list/missions-list.component';
import { BountiesViewComponent } from './pages/missions/bounties-view/bounties-view.component';


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
