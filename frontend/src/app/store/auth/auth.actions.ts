import { createAction, props } from '@ngrx/store';
import { UserType } from '../../interfaces/types';

export const loginAction = createAction(
  '[Auth] Login',
  props<{ user: UserType }>()
);
export const logoutAction = createAction('[Auth] Logout');
