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

  token = "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiTUFOQUdFUiIsInN1YiI6Im1pc3NseWx5ZHU3NUBob3RtYWlsLmZyIiwiaWF0IjoxNzI0NDI1MjE3LCJleHAiOjE3MjQ0NDMyMTd9.9_4urE-7KCBkf9R7oPEnW5oaH5hX_WskJ6GGmeIOvXdW3bRdLwMrUG_gfFgZrZNN";

  // Get the list of missions
  getMissions(page: number, size?: number, searchbar?: string): Observable<any> {

    
    let params = new HttpParams()
    .set('page', page.toString());
    
    if(size){
      params = params.set('size', size.toString());
    }
    if(searchbar && searchbar.trim() !== ""){
      params = params.set('searchbar', searchbar);
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    
    return this.http.get<any >(this.apiURL, { headers, params }).pipe(
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
        console.error(`Mission with ID ${id} not found`, error);
        return throwError(() => new Error('Not Found'));
      })
    );
  }

  // Create a mission. Return the newly added mission or throws an error if the method fails.
  addMission(mission: Mission): Observable<Mission> {
    return this.http.post<Mission>(this.apiURL, mission).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Mission ${mission.label} cannot be created`, error);
        return throwError(() => new Error('Creation error'));
      })
    );
  }

  // Update a mission by its id. Return the updated mission or throws an error if the mission is not found.
  updateMission(mission: Mission): Observable<Mission> {
    return this.http.put<Mission>(`${this.apiURL}/${mission.id}`, mission).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Mission with ID ${mission.id} not found`, error);
        return throwError(() => new Error('Not Found'));
      })
    );
  }

  // Delete a mission by its id. Throws an error if the mission is not found.
  deleteMission(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`Mission with ID ${id} not found`, error);
        return throwError(() => new Error('Not Found'));
      })
    );
  }
  // Handle errors
  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

}
