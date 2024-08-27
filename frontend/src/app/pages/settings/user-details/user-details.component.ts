import { Component } from '@angular/core';
import { LayoutComponent } from '../../../layout/layout.component';
import { CustomButtonLinkComponent } from '../../../components/button-link/custom-button-link.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faEnvelope,
  faKey,
  faUser,
  IconDefinition,
} from '@fortawesome/free-solid-svg-icons';
import { CustomButtonComponent } from '../../../components/buttons/custom-button.component';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [
    LayoutComponent,
    CustomButtonLinkComponent,
    FontAwesomeModule,
    CustomButtonComponent,
  ],
  templateUrl: './user-details.component.html',
})
export class UserDetailsComponent {
  faEnvelopeIcon: IconDefinition = faEnvelope;
  faUserIcon: IconDefinition = faUser;
  faKeyIcon: IconDefinition = faKey;
}
