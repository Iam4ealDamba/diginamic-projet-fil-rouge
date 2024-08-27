import { Component, OnInit } from '@angular/core';
import {
  Router,
  RouterLink,
  RouterModule,
  ActivatedRoute,
} from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';
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
import { SidebarMenuType } from '../interfaces/types';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [FontAwesomeModule, CommonModule, RouterModule],
  providers: [Router, CookieService, RouterLink],
  templateUrl: './layout.component.html',
})
export class LayoutComponent implements OnInit {
  // FontAwesome icons
  faHouseIcon: IconDefinition = faHouse;
  faUsersIcon: IconDefinition = faUsers;
  faBookIcon: IconDefinition = faBook;
  faSuitcaseIcon: IconDefinition = faSuitcase;
  faCoinsIcon: IconDefinition = faCoins;
  faScrollIcon: IconDefinition = faScroll;
  faGearIcon: IconDefinition = faGear;
  faLogoutIcon: IconDefinition = faRightFromBracket;

  // Sidebar Menu
  sidebarMenu: SidebarMenuType = {
    top: [
      {
        name: 'Tableau de bord',
        link: '',
        icon: this.faHouseIcon,
        active: false,
      },
      {
        icon: this.faUsersIcon,
        link: '/collaborators',
        name: 'Collaborateurs',
        active: false,
      },
      {
        icon: this.faBookIcon,
        link: '/missions',
        name: 'Missions',
        active: false,
      },
      {
        icon: this.faSuitcaseIcon,
        link: '/mission-nature',
        name: 'Nature des missions',
        active: false,
      },
      {
        icon: this.faScrollIcon,
        link: '/expenses',
        name: 'Notes de frais',
        active: false,
      },
    ],
    bottom: [
      {
        icon: this.faGearIcon,
        link: '/settings/user-details',
        name: 'Paramètres',
        active: false,
      },
      {
        icon: this.faLogoutIcon,
        link: '/logout',
        name: 'Deconnexion',
        active: false,
      },
    ],
  };

  // Active page
  activePage: string = '';

  // User Status
  userStatus: boolean = false;

  /**
   * constructor
   */
  constructor(
    private router: Router,
    private cookie: CookieService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    // Redirection to Login page if user is not connected
    this.activePage = this.router.url.replace('/', '').split('/')[0];

    this.userStatus = this.cookie.check('jwt_token');
    if (!this.userStatus) this.router.navigateByUrl('/login');

    // Else set active page
    for (const item of this.sidebarMenu.top) {
      if (item.link === this.activePage) {
        item.active = true;
      }
    }

    for (const item of this.sidebarMenu.bottom) {
      if (item.link.replace('/', '').split('/')[0] === this.activePage) {
        item.active = true;
      }
    }
  }

  async logout() {
    this.cookie.delete('jwt_token');
    await this.router.navigate(['/login']);
  }
}
