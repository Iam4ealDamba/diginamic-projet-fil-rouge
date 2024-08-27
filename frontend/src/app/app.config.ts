import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptor } from './middlewares/token/token.interceptor';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AuthReducer } from './store/auth/auth.reducer';
import { environment } from '../environments/environment.development';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideStore({
      auth: AuthReducer,
    }), // required store providers (ngrx store)
    provideStoreDevtools({ maxAge: 25, logOnly: environment.production }),
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
