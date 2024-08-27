import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { provideStore } from '@ngrx/store';
import { provideToastr } from 'ngx-toastr';
import { routes } from './app.routes';
import { tokenInterceptor } from './middlewares/token/token.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideStore(), // required store providers (ngrx store)
    provideHttpClient(withInterceptors([tokenInterceptor])), // required http interceptor
    provideAnimations(), // required animations providers
    provideToastr({
      positionClass: 'toast-bottom-right',
      progressBar: true,
      progressAnimation: 'decreasing',
      autoDismiss: true,
      preventDuplicates: true,
      timeOut: 3000,
    }), // Toastr providers
    FontAwesomeModule,
  ],
};
