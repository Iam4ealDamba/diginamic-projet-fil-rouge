import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  standalone: true,
  imports: [FontAwesomeModule],
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
})
export class Button {
  @Input() text!: string;
  @Input() type?: 'submit' | 'button' | 'reset' = 'button';
  @Input() theme?: 'regular' | 'error' | 'borderless' = 'regular';
  @Input() iconLeft?: IconDefinition;
  @Input() iconRight?: IconDefinition;

  getRegularTheme(): string {
    let _theme = '';

    switch (this.theme) {
      case 'error':
        _theme = 'bg-tw_error text-tw_black';
        break;
      case 'borderless':
        _theme = 'text-tw_black';
        break;
      default:
        _theme = 'bg-tw_black text-tw_white';
        break;
    }

    console.log(_theme);

    return _theme;
  }
}
