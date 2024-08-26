import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserLoginDTOType, UserRegisterDTOType } from '../../interfaces/types';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  /**
   *  Constructor
   */
  constructor(private http: HttpClient) {}

  login(loginDto: UserLoginDTOType): Observable<string> {
    // Appel à l'API pour obtenir un token d'authentification
    return this.http.post(environment.apiURL + '/auth/login', loginDto, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'text',
    });
  }

  register(registerDto: UserRegisterDTOType): Observable<any> {
    //
    const data = {
      ...registerDto,
      birthdate: new Date().toJSON().split('T')[0],
    };

    // Appel à l'API pour obtenir un token d'authentification
    return this.http.post(environment.apiURL + '/auth/register', data);
  }

  currentUser(): Observable<any> {
    return this.http.get(environment.apiURL + '/auth/me');
  }
}
