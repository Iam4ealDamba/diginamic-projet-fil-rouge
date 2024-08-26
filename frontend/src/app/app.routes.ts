import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ExpenseListComponent } from './pages/expense-list/expense-list.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full',
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
    path: 'expense/:expenseId',
    component: ExpenseListComponent
  },
  {
    path: 'expense/:expenseId/:lineId',
    component: ExpenseListComponent
  },
  {
    path: 'expense/:expenseId/add',
    component: ExpenseListComponent
  }
];
