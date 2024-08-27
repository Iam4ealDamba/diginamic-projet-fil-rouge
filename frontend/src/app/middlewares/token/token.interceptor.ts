import {
  HttpClient,
  HttpEvent,
  HttpHandlerFn,
  HttpHeaders,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { jwtDecode } from 'jwt-decode';

export function tokenInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {
  // The CookieService is injected in the variable
  const http = inject(HttpClient);
  const cookieService = inject(CookieService);

  // Retrieve the token from cookie
  const token = cookieService.get('jwt_token');

  // If the token is empty/do not exist, return the original request
  if (!token) {
    return next(req);
  }

  // If it exist, but is expired/invalid, return the original request
  if (!verifyToken(token)) {
    cookieService.delete('jwt_token');
    return next(req);
  }

  // If the token is valid, refresh it
  let newToken = '';
  http
    .get<any>(`${environment.apiURL}/auth/refresh`, {
      headers: {
        Authorization: 'Bearer ' + token,
      },
    })
    .subscribe((data: string) => {
      newToken = data;
      cookieService.set('jwt_token', data);
    });

  // Add the new token to the header
  const headers = new HttpHeaders({
    Authorization: 'Bearer ' + newToken,
  });
  const newReq = req.clone({
    headers,
  });

  // Return the new request
  return next(newReq);
}

function verifyToken(token: string): boolean {
  const today = new Date().getTime() / 1000;
  const decoded = jwtDecode(token);

  if (!decoded.exp) return false;

  return decoded.exp > today;
}
