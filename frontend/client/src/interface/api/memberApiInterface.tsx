import { common } from './commonApiInterface';

export interface AuthRequest {
  dispatch: Function;
}

export interface LoginResponse extends common {
  data: {
    accessToken: string;
    refreshToken: string;
    userName: string;
  };
}

export interface LoginRequest extends AuthRequest {
  email: string;
  password: string;
}

export interface LogoutRequest extends AuthRequest {}

export interface Usersdata {
  id: string;
  name: string;
  email: string;
  createdDate: Date;
}

export interface UsersResponse extends common {
  data: Usersdata[];
}
