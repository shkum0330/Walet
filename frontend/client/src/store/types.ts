export interface AuthState {
  accessToken: string | null;
  refreshToken: string | null;
  userName: string | null;
}

export interface SetTokensAction {
  type: 'SET_TOKENS';
  payload: {
    accessToken: string | null;
    refreshToken: string | null;
    userName: string | null;
  };
}

export interface LogoutAction {
  type: 'LOGOUT';
}

export type ActionTypes = SetTokensAction | LogoutAction;
