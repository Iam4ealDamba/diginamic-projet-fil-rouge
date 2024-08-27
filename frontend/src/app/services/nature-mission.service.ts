import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NatureMission } from '../interfaces/nature-mission.interface';

@Injectable({
  providedIn: 'root',
})
export class NatureMissionService {
  private apiUrl = 'http://localhost:8080/api/mission/natures';

  constructor(private http: HttpClient) {}

  // Méthode pour récupérer toutes les natures de mission
  getNatureMissions(): Observable<NatureMission[]> {
    return this.http.get<NatureMission[]>(`${this.apiUrl}/naturemissions`, {
      headers: new HttpHeaders({
        Authorization: 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkB0ZXN0LnRlc3QiLCJpYXQiOjE3MjQ2ODYwNDYsImV4cCI6MTcyNDY4Nzg0Nn0.ywrGaL40kF5b-BwJaINYlQ8cgxdySqoIv_xztA0K7SlyZhwg7Qc_C1yLOG5qqI5e', // Remplacez par votre jeton JWT
      }),
    });
  }

  // Méthode pour récupérer une nature de mission par ID
  getNatureMission(id: number): Observable<NatureMission> {
    return this.http.get<NatureMission>(`${this.apiUrl}/${id}`);
  }

  // Méthode pour créer une nouvelle nature de mission
  createNatureMission(natureMission: NatureMission): Observable<NatureMission> {
    return this.http.post<NatureMission>(this.apiUrl, natureMission, {
      headers: new HttpHeaders({
        Authorization:
          'Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkB0ZXN0LnRlc3QiLCJpYXQiOjE3MjQ2ODYwNDYsImV4cCI6MTcyNDY4Nzg0Nn0.ywrGaL40kF5b-BwJaINYlQ8cgxdySqoIv_xztA0K7SlyZhwg7Qc_C1yLOG5qqI5e',
        'Content-type': 'application/json',
      }),
      responseType: 'json',
    });
  }

  // Méthode pour mettre à jour une nature de mission existante
  updateNatureMission(id: number, mission: NatureMission): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, mission);
  }

  // Méthode pour supprimer une nature de mission
  deleteNatureMission(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
