import { Component, OnInit } from '@angular/core';
import { ExpenseListComponent } from '../../components/expense-list/expense-list.component';
import { CommonModule } from '@angular/common';
import { Expense, ExpenseLine, ExpenseService } from '../../expense.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EMPTY, Observable } from 'rxjs';

@Component({
  selector: 'app-expense-view',
  standalone: true,
  imports: [CommonModule, ExpenseListComponent],
  templateUrl: './expense-view.component.html',
  styleUrl: './expense-view.component.scss'
})
export class ExpenseViewComponent implements OnInit {
  expense$: Observable<Expense> = EMPTY;
  expenseId!: number;  

  constructor(private expenseService: ExpenseService, private router: Router,  private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('expenseId');
      if (id) {
        this.expenseId = +id;
        this.loadExpense(this.expenseId);
      } else {
        console.error('Expense ID is missing in the URL');
      }
    });
  }

  exportExpenseToPdf(): void {
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

  private loadExpense(expenseId: number): void {
    if (!expenseId) {
      console.error('Expense ID is required to load expense data.');
      return;
    }
    this.expense$ = this.expenseService.getExpenseById(expenseId.toString(), "token");
    this.expense$.subscribe({
      next: (expense) => {
        console.log('Expense data loaded successfully:', expense);
      },
      error: (error) => {
        console.error('Error loading expense data:', error);
      }
    });
  }

  onRefreshExpense(): void {
    this.loadExpense(this.expenseId);  // Reload expense data
  }
}