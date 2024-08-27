import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideClientHydration } from '@angular/platform-browser';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptor } from './middlewares/token/token.interceptor';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { BarController, Legend, Colors, BarElement, CategoryScale, LinearScale, Title, Tooltip } from 'chart.js';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AuthReducer } from './store/auth/auth.reducer';
import { environment } from '../environments/environment.development';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideStore(), // required store providers (ngrx store)
    provideClientHydration(),
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
    FontAwesomeModule, // FontAwesome module
    provideCharts(withDefaultRegisterables()), // ng2-charts provider with default registerables
    provideCharts({
      registerables: [
        BarController,
        CategoryScale,
        LinearScale,
        BarElement,
        Title,
        Tooltip,
        Legend,
        Colors
      ],
    }), // Additional chart.js registerables
  ],
};
