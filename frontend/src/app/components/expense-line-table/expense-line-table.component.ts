import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ExpenseLine } from '../../expense.service';
import { CommonModule } from '@angular/common';
import { ExpenseLineFormComponent } from '../expense-line-form/expense-line-form.component';

@Component({
  selector: 'app-expense-line-table',
  standalone: true,
  imports: [CommonModule, ExpenseLineFormComponent],
  templateUrl: './expense-line-table.component.html',
  styleUrl: './expense-line-table.component.scss'
})
export class ExpenseLineTableComponent {


  @Input() expenseLines: ExpenseLine[] = [];
  @Input() expenseId!: number;
  @Input() expenseLineId!: number;
  @Input() isNewLine: boolean=false;

  @Output() modifyLine = new EventEmitter<number>();
  @Output() removeLine = new EventEmitter<number>();
  @Output() formSubmit = new EventEmitter<ExpenseLine>();


  onModifyLine(id: number) {
    this.modifyLine.emit(id);
    this.isNewLine = false;

  }

  onRemoveLine(id: number) {
    this.removeLine.emit(id);
  }
  onFormSubmit(expenseLine: ExpenseLine) {
    this.formSubmit.emit(expenseLine);
    console.log("dans le form composant table")
    this.isNewLine = false;
    this.expenseLineId = 0;
  }

  onNewLine() {
    this.isNewLine = true;
    this.expenseLineId = 0;
  }

  closeNewLine() {
    this.isNewLine = false;
  }

    
}

