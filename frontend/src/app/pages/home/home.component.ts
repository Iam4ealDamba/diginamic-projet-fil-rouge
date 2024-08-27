import { Component } from '@angular/core';
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

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FontAwesomeModule, LayoutComponent],
  providers: [Router],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  faHouseIcon: IconDefinition = faHouse;
  faUsersIcon: IconDefinition = faUsers;
  faBookIcon: IconDefinition = faBook;
  faSuitcaseIcon: IconDefinition = faSuitcase;
  faCoinsIcon: IconDefinition = faCoins;
  faScrollIcon: IconDefinition = faScroll;
  faGearIcon: IconDefinition = faGear;
  faLogoutIcon: IconDefinition = faRightFromBracket;

  /**
   * constructor
   */
  constructor(private router: Router) {
    // router.navigateByUrl('login');
  }
}
