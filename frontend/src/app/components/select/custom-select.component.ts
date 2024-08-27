import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  standalone: true,
  selector: 'app-custom-select-component',
  templateUrl: './custom-select.component.html',
  imports: [FontAwesomeModule],
  providers: [],
})
export class CustomSelectComponent {
  @Input() title: string = '';
  @Input() theme?: 'regular' | 'border' = 'regular';
  @Input() icon?: any;

  getRegularTheme(): string {
    let _theme = '';

    switch (this.theme) {
      case 'border':
        _theme = 'text-tw_black border-2';
        break;
      default:
        _theme = 'bg-tw_black text-tw_white';
        break;
    }

    return _theme;
  }
}
