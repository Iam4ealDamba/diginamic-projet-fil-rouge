import { Component } from '@angular/core';
import {
  FontAwesomeModule,
  IconDefinition,
} from '@fortawesome/angular-fontawesome';
import {
  faClock,
  faSearch,
  faUserPlus,
} from '@fortawesome/free-solid-svg-icons';

import { LayoutComponent } from '../../../layout/layout.component';
import { CustomButtonComponent } from '../../../components/buttons/custom-button.component';
import { CustomButtonLinkComponent } from '../../../components/button-link/custom-button-link.component';
import { CustomSelectComponent } from '../../../components/select/custom-select.component';

@Component({
  selector: 'app-collaborators-home',
  standalone: true,
  imports: [
    LayoutComponent,
    CustomButtonComponent,
    CustomButtonLinkComponent,
    FontAwesomeModule,
    CustomSelectComponent,
  ],
  templateUrl: './collaborators-home.component.html',
  styleUrl: './collaborators-home.component.scss',
})
export class CollaboratorsHomeComponent {
  // FontAwesome Icons
  faUserPlus: IconDefinition = faUserPlus;
  faClockIcon: IconDefinition = faClock;
  faSearchIcon: IconDefinition = faSearch;
}
