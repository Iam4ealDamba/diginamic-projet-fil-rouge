import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Expense, ExpenseLine, ExpenseService } from '../../expense.service';
import { EMPTY, Observable, Subscription, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpenseLineTableComponent } from '../../components/expense-line-table/expense-line-table.component';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [CommonModule, ExpenseLineTableComponent],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.scss'
})
export class ExpenseListComponent implements OnInit {
  expense$: Observable<Expense>= EMPTY;
  expenseLineId!: number;
  isNewLine: boolean=false;
  expenseId!: number;
  private routeSub!: Subscription;


  constructor(private expenseService: ExpenseService, private router: Router, private route: ActivatedRoute){
    
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.pipe(
      switchMap(params => {
        this.expenseId = +params['expenseId']; // Récupère et convertit 'expenseId' en nombre
  
        // Met à jour expenseLineId uniquement si 'lineId' est présent, sinon laisse-le inchangé
        const lineIdParam = params['lineId'];
      this.expenseLineId = lineIdParam ? +lineIdParam : this.expenseLineId;
        this.isNewLine = params['lineId'] === 'add'; // Détermine si la ligne est une nouvelle ligne ('add')
  
        // Retourne un Observable d'Expense obtenu par id
        return this.expenseService.getExpenseById(this.expenseId.toString(), "token");
      })
    ).subscribe(expense => {
      this.expense$ = this.expenseService.getExpenseById(this.expenseId.toString(), "token");
    });
  }
  newLine() {
    this.router.navigate([`/expense/${this.expenseId}/add`]);
  }
  


  modifyExpenseLine(id: number) {
    this.router.navigate([`/expense/${this.expenseId}/${id}`]);
  }
  
  removeExpenseLine(id: number): void {
    this.expenseService.removeLine(id.toString()).subscribe(() => {
      this.expense$ = this.expenseService.getExpenseById(id.toString(), "token");
    })
  }

  updateExpenseLine(expenseLine: ExpenseLine) {
    if(expenseLine.id){
      this.expenseService.updateLine(expenseLine.id.toString(), expenseLine, "token").subscribe({
        next: () => this.router.navigate([`/expense/${this.expenseId}`]),
        error: (error) => console.error('Error updating expense line:', error)
      });
    }
   
  }

  addExpenseLine(expenseLine: ExpenseLine) {
    this.expenseService.addLine(expenseLine).subscribe({
      next: () => this.router.navigate([`/expense/${this.expenseId}`]),
      error: (error) => console.error('Error adding expense line:', error)
    });
  }

  exportExpenseToPdf() {
    this.expenseService.exportExpensePdf(this.expenseId.toString(), "token").subscribe({
      next: (response: Blob) => {
        const url = window.URL.createObjectURL(response);
        const a = document.createElement('a');
        a.href = url;
        a.download = `expense_${this.expenseId}.pdf`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      },
      error: (error) => console.error('Error when exporting pdf:', error)
    });
  }

  onFormSubmit(expenseLine: ExpenseLine) {
    console.log("en haut")
    if (this.expenseLineId) {
      this.updateExpenseLine(expenseLine);
    } else {
      this.addExpenseLine(expenseLine);
    }
  }


}
