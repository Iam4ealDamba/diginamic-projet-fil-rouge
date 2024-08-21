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

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [FontAwesomeModule],
  providers: [Router],
  templateUrl: './layout.component.html',
})
export class LayoutComponent {
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
