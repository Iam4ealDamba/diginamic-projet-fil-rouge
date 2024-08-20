import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './views/dashboard/dashboard.component';


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
  // {
  //   path: 'dashboard-missions',
  //   component:DashboardComponent,
  // },

];
