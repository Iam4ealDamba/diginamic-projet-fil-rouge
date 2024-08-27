import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { catchError, Observable, of, retry } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import ms from 'ms';
import { AuthService } from '../../services/auth/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  // The CookieService is injected in the variable
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

  req = req.clone({
    headers: new HttpHeaders({
      Authorization: 'Bearer ' + token,
    }),
  });

  // Return the new request
  return next(req);
};

function verifyToken(token: string): boolean {
  const today = new Date().getTime() / 1000;
  const decoded = jwtDecode(token);

  if (!decoded.exp) return false;

  return decoded.exp > today;
}
