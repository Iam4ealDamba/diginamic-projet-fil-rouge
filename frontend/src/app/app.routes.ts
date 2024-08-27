// app.routes.ts
import { Routes } from '@angular/router';
import { AdminMissionListComponent } from '../../../frontend/src/app/pages/nature-mission/admin-mission-list/admin-mission-list.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { NatureMissionCreateComponent } from './pages/nature-mission/nature-mission-create/nature-mission-create.component';
import { NatureMissionEditComponent } from './pages/nature-mission/nature-mission-edit/nature-mission-edit.component';
import { NatureMissionListComponent } from './pages/nature-mission/nature-mission-list/nature-mission-list.component';
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
];
