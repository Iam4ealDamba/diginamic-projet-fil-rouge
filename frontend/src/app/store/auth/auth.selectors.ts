import { createSelector } from '@ngrx/store';
import { AuthState } from './auth.reducer';
import { AuthReducer } from './auth.reducer';

export const authSelector = (state: AuthState) => state.user;
