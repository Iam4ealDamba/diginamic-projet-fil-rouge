import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faHouse,
  faUsers,
  faBook,
  faSuitcase,
  faScroll,
  faCoins,
  faGear,
  faRightFromBracket,
  IconDefinition,
} from '@fortawesome/free-solid-svg-icons';
import { LayoutComponent } from '../../layout/layout.component';
import { UserType } from '../../interfaces/types';
import { Store } from '@ngrx/store';
import { AuthStateReducer } from '../../store/auth/auth.reducer';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth/auth.service';
import { loginAction } from '../../store/auth/auth.actions';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FontAwesomeModule, LayoutComponent],
  providers: [Router],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  // FontAwesome Icons
  faHouseIcon: IconDefinition = faHouse;
  faUsersIcon: IconDefinition = faUsers;
  faBookIcon: IconDefinition = faBook;
  faSuitcaseIcon: IconDefinition = faSuitcase;
  faCoinsIcon: IconDefinition = faCoins;
  faScrollIcon: IconDefinition = faScroll;
  faGearIcon: IconDefinition = faGear;
  faLogoutIcon: IconDefinition = faRightFromBracket;

  // User
  user: Observable<UserType> | undefined;

  /**
   * constructor
   */
  constructor(
    private router: Router,
    private store: Store<AuthStateReducer>,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.authService.currentUser().subscribe((user) => {
      this.store.dispatch(loginAction({ user }));
      this.user = this.store.select('user') as Observable<UserType>;
    });
  }
}
