import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserLoginDTOType, UserRegisterDTOType } from '../../interfaces/types';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  /**
   *  Constructor
   */
  constructor(private http: HttpClient) {}

  // currentUser(loginDto: UserLoginDTOType): Observable<any> {
  //   // Appel à l'API pour obtenir un token d'authentification
  //   return this.http.post(environment.apiURL + '/auth/login', loginDto);
  // }

  // register(registerDto: UserRegisterDTOType): Observable<any> {
  //   //
  //   const data = {
  //     ...registerDto,
  //     birthdate: new Date().toJSON().split('T')[0],
  //   };

  //   // Appel à l'API pour obtenir un token d'authentification
  //   return this.http.post(environment.apiURL + '/auth/login', data);
  // }
}
