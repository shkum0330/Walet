import { LOGOUT, SET_TOKENS } from '../actions/authActions';
import { ActionTypes, AuthState } from '../types';

const initialState: AuthState = {
  accessToken: null,
  refreshToken: null,
  userName: null,
};

const authReducer = (
  state: AuthState = initialState,
  action: ActionTypes,
): AuthState => {
  switch (action.type) {
    case SET_TOKENS:
      return {
        ...state,
        accessToken: action.payload.accessToken,
        refreshToken: action.payload.refreshToken,
        userName: action.payload.userName as string,
      };
    case LOGOUT:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

export default authReducer;
