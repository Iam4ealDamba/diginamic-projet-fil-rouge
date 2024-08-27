import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  UserLoginDTOType,
  UserRegisterDTOType,
  UserType,
} from '../../interfaces/types';
import { environment } from '../../../environments/environment.development';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  /**
   *  Constructor
   */
  constructor(private http: HttpClient, private cookie: CookieService) {}

  login(loginDto: UserLoginDTOType): Observable<string> {
    // Appel à l'API pour obtenir un token d'authentification
    return this.http.post(environment.apiURL + '/auth/login', loginDto, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'text',
    });
  }

  register(registerDto: UserRegisterDTOType): Observable<string> {
    //
    const data = {
      ...registerDto,
      birthdate: new Date().toJSON().split('T')[0],
    };

    // Appel à l'API pour obtenir un token d'authentification
    return this.http.post(environment.apiURL + '/auth/register', data, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'text',
    });
  }

  refresh(): Observable<string> {
    const token = this.cookie.get('jwt_token');

    // Appel à l'API pour obtenir un token d'authentification
    return this.http.get(environment.apiURL + '/auth/refresh', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
      responseType: 'text',
    });
  }

  currentUser(): Observable<UserType> {
    const token = this.cookie.get('jwt_token');

    return this.http.get(environment.apiURL + '/auth/me', {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
      responseType: 'json',
    }) as Observable<UserType>;
  }

  update(id: number, user: UserType): Observable<any> {
    return this.http.put(environment.apiURL + `/users/${id}`, user);
  }

  updatePassword(
    id: number,
    data: {
      oldPassword: string;
      newPassword: string;
    }
  ): Observable<string> {
    return this.http.put(environment.apiURL + `/users/password/${id}`, data, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      responseType: 'text',
    });
  }
}
