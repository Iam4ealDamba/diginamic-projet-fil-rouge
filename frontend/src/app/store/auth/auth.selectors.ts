import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthStateReducer } from './auth.reducer';

const selector = createFeatureSelector<AuthStateReducer>('auth');

export const authSelector = createSelector(selector, (auth) => auth.user);
