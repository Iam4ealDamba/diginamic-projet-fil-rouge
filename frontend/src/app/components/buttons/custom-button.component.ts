import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  standalone: true,
  imports: [FontAwesomeModule],
  selector: 'app-custom-button-component',
  templateUrl: './custom-button.component.html',
})
export class CustomButtonComponent { 
  @Input() type?: 'submit' | 'button' | 'reset' = 'button';
  @Input() theme?: 'regular' | 'error' | 'border' = 'regular';
  @Input() iconLeft: any;
  @Input() iconRight: any;

  getRegularTheme(): string {
    let _theme = '';

    switch (this.theme) {
      case 'error':
        _theme = 'bg-tw_error text-tw_black';
        break;
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
