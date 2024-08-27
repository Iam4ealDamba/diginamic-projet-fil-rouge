import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

export interface Expense {
  /** The ID of the expense */
  id: number;

  /** The date of the expense */
  date: Date;

  /** The status of the expense */
  status: string;

  /** The expense lines associated with this expense */
  expenseLines: ExpenseLine[];
}

export interface ExpenseLine {
  /** The ID of the expense */
  id: number | null;

  /** The date of the expense line */
  date: Date;

  /** The TVA of the expense line */
  tva: number;

  /** The amount of the expense line */
  amount: number;

  /** The type of this expense line */
  expenseType: string;
}

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private apiUrl: string = 'http://localhost:8080/api';

  private tokenTest: string = "eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImpvaG4uZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzI0NzYyNTIxLCJleHAiOjE3MjQ3NjQzMjF9.vt2Wd0enbRFeLH56BjpEWxVntVtdf6ubY5Bu0npq_nQs4_iYWawtgvWEQ2N85q8r"

  constructor(private http: HttpClient) {
   }

  getExpenseById(id: string, token: string) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.tokenTest}`
    });
  
    return this.http.get<Expense>(`${this.apiUrl}/expenses/${id}`, { headers }).pipe(
      catchError(error => {
        console.error(`Expense with ID ${id} not found`, error);
        return throwError(() => new Error('Not Found'));
      })
    );
  }

  addLine(expenseLine: ExpenseLine, idExpense: string):Observable<void>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json', 
      'Authorization': `Bearer ${this.tokenTest}`
    });
    return this.http.post<void>(`${this.apiUrl}/expenseLines/${idExpense}`, expenseLine, { headers, responseType: 'text' as 'json' })
    .pipe(
      catchError(error => {
        console.error('Error adding expense line:', error);
        return throwError(() => new Error('Failed to add expense line'));
      })
    );

  }

  removeLine(id: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.tokenTest}`
    });
    return this.http.delete<void>(`${this.apiUrl}/expenseLines/${id}`, { headers, responseType: 'text' as 'json' });
}

updateLine(id: string, updatedLine: ExpenseLine, token:string): Observable<ExpenseLine> {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': `Bearer ${this.tokenTest}` }); // Définition des headers pour la requête
  console.log(`Updating expense line with ID ${id}`, updatedLine);
  return this.http.put<ExpenseLine>(`${this.apiUrl}/expenseLines/${id}`, updatedLine, { headers, responseType: 'text' as 'json' }) // Met à jour la ligne existante
    .pipe(
      catchError(error => {
        console.error(`Error updating expense line with ID ${id}: `, error); // Gestion des erreurs
        return throwError(() => new Error('Failed to update expense line')); // Retourne une erreur si la mise à jour échoue
      })
    );
}

getExpenseLineById(id: string, token: string) {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenTest}`
  });

  return this.http.get<ExpenseLine>(`${this.apiUrl}/expenseLines/${id}`, { headers }).pipe(
    catchError(error => {
      console.error(`ExpenseLine with ID ${id} not found`, error);
      return throwError(() => new Error('Not Found'));
    })
  );
}

exportExpensePdf(id: string, token: string) {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenTest}`
  });

  return this.http.get(`${this.apiUrl}/expenses/pdf/${id}`, { headers, responseType: 'blob' }).pipe(
    catchError(error => {
      console.error(`Error fetching PDF for Expense with ID ${id}`, error);
      return throwError(() => new Error('Failed to fetch PDF'));
    })
  );
}

}
