import {  createReducer, on } from '@ngrx/store';
import { UserType } from '../../interfaces/types';
import { loginAction, logoutAction } from './auth.actions';

export type AuthState = {
  user: UserType | null;
};

export const initialState: AuthState = {
  user: null,
};

export const AuthReducer = createReducer(
  // Initial state
  initialState,
  // Login a user
  on(loginAction, (state, { user }) => ({ ...state, user })),
  // Logout a user
  on(logoutAction, (state) => ({ ...state, user: null }))
);
