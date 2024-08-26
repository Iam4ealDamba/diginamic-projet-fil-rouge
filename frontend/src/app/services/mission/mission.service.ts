import { Injectable } from '@angular/core';
import { Mission } from '../../models/Mission';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environnement } from '../../../environnements/environnement';

@Injectable({
  providedIn: 'root'
})
export class MissionService {

  //API url
  private apiURL = environnement.apiUrlMissions;

  constructor(private http: HttpClient) {}

  token = "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiTUFOQUdFUiIsInN1YiI6Im1pc3NseWx5ZHU3NUBob3RtYWlsLmZyIiwiaWF0IjoxNzI0NjIwMjI0LCJleHAiOjE3MjQ2MzgyMjR9.D0BfwGP0gqRfUnOO4F8spIZCnAYAw1d6ahNFBv-6BIev6yAjY8RX-yhQSbIwtgIM";

  getMissions(queryParams: { page: number, size?: number, searchbar?: string, withExpense?: boolean }): Observable<any> {

    const { page, size, searchbar, withExpense } = queryParams;
 
    let params = new HttpParams()
    .set('page', page.toString());
    
    if(size){
      params = params.set('size', size.toString());
    }
    if(searchbar && searchbar.trim() !== ""){
      params = params.set('searchbar', searchbar);
    }
    if(withExpense){
      params = params.set('withExpense', withExpense);
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    
    return this.http.get<any>(this.apiURL, { headers, params }).pipe(
      catchError(this.handleError)
    );
  }

   // Get a mission by its id. Return the mission or throws an error if the mission is not found.
   getMissionById(id: string): Observable<Mission> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get<Mission>(`${this.apiURL}/${id}`, {headers}).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Error on getMission with ID ${id}:`, error);
        return throwError(() => new Error( error.message));
      })
    );
  }

  // Create a mission. Return the newly added mission or throws an error if the method fails.
  addMission(mission: Mission): Observable<Mission> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.post<Mission>(this.apiURL, mission, {headers}).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Mission ${mission.label} cannot be created`, error);
        return throwError(() => new Error( error.message));
      })
    );
  }

  // Update a mission by its id. Return the updated mission or throws an error if the mission is not found.
  updateMission(mission: Mission): Observable<Mission> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });

    return this.http.put<Mission>(`${this.apiURL}/${mission.id}`, mission, {headers}).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Error on updateMission with ID ${mission.id} :`, error);
        return throwError(() => new Error( error.message));
      })
    );
  }

  // Delete a mission by its id. Throws an error if the mission is not found.
  deleteMission(id: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });

    return this.http.delete<void>(`${this.apiURL}/${id}`, {headers}).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(error);
        return throwError(() => new Error(error.message));
      })
    );
  }
  // Handle errors
  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error.message));
  }

}
