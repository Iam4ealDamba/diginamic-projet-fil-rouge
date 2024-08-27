import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Expense, ExpenseLine, ExpenseService } from '../../expense.service';
import { EMPTY, Observable, Subscription, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpenseLineTableComponent } from '../expense-line-table/expense-line-table.component';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [CommonModule, ExpenseLineTableComponent],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.scss'
})
export class ExpenseListComponent {
  @Input() expense$: Observable<Expense>= EMPTY;
  expenseLineId!: number;
  isNewLine: boolean=false;
  @Input() expenseId!: number;
  @Output() refreshExpense = new EventEmitter<void>();


  constructor(private expenseService: ExpenseService, private router: Router){
    
  }

  modifyExpenseLine(id: number) {
    this.expenseLineId = id;
    this.isNewLine = false;
  }
  
  removeExpenseLine(id: number): void {
    this.expenseService.removeLine(id.toString()).subscribe({
      next: () => {
        this.refreshExpense.emit();
        this.expenseLineId = 0;
        console.log('Expense line updated successfully');
      },
      error: (error) => console.error('Error updating expense line:', error)
    });
  }
  


  updateExpenseLine(expenseLine: ExpenseLine) {
    if (expenseLine.id) {
      this.expenseService.updateLine(expenseLine.id.toString(), expenseLine, "token").subscribe({
        next: () => {
          this.refreshExpense.emit();
          this.expenseLineId = 0;
          console.log('Expense line updated successfully');
        },
        error: (error) => console.error('Error updating expense line:', error)
      });
    }
  }

  addExpenseLine(expenseLine: ExpenseLine) {
    this.expenseService.addLine(expenseLine, this.expenseId.toString()).subscribe({
      next: () => {
        this.refreshExpense.emit();
        this.expenseLineId = 0;
        console.log('Expense line updated successfully');
      },
      error: (error) => console.error('Error updating expense line:', error)
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
