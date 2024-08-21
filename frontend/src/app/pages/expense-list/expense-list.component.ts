import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Expense, ExpenseService } from '../../expense.service';
import { EMPTY, Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.scss'
})
export class ExpenseListComponent implements OnInit {
  expense$: Observable<Expense>= EMPTY;

  constructor(private expenseService: ExpenseService, private router: Router, private route: ActivatedRoute){
    
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === null) {
      console.error('Invalid ID: null');
      return;
    }
    this.expense$ = this.expenseService.getExpenseById(id, "token");
  }
  

  addExpenseLine(id: string) {
    this.expense$.subscribe({
      next: (expense) => {
    console.log(`Navigating to /expenseLine/add/${id}`);
    this.router.navigate([`/expenseLine/add/${id}`]);
  },
  error: (error) => {
    console.error('Error fetching expenseLine:', error);
  }
});  
  }

  modifyExpenseLine() {
    this.expense$.subscribe({
      next: (expense) => {
        console.log(`Navigating to /expenseLine/modify/${expense.id}`);
        this.router.navigate([`/expenseLine/modify/${expense.id}`]);
      },
      error: (error) => {
        console.error('Error fetching expenseLine:', error);
      }
    }); 
  }
  
  RemoveExpenseLine(id: string): void {
    this.expenseService.removeLine(id).subscribe(() => {
      this.expense$ = this.expenseService.getExpenseById(id, "token");
    })
  }

}
