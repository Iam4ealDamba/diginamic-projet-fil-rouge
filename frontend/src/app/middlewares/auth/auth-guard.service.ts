import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
  /**
   *  constructor
   *
   * @param {Router} router - Router service
   * @param {CookieService} cookie - Cookie service
   */
  constructor(private router: Router, private cookie: CookieService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    let userStatus = this.cookie.check('jwt_token');
    if (!userStatus) this.router.navigate(['/login']);

    return userStatus;
  }
}
