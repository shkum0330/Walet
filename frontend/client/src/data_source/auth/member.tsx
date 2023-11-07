import axios, { AxiosError } from 'axios';
import { AUTH_URI } from '../apiInfo';
import {
  LogoutRequest,
  LoginResponse,
  LoginRequest,
  UsersResponse,
  UserSerachResponse,
} from '../../interface/api/memberApiInterface';
import { logout } from '../../store/actions/authActions';
import { setTokens } from '../../store/store';

export async function LoginAPI({ email, password, dispatch }: LoginRequest) {
  const loginURI = `${AUTH_URI}/login`;
  let data = null;
  if (email && password) {
    await axios
      .post<LoginResponse>(loginURI, { email, password })
      .then(response => {
        const { accessToken, refreshToken, userName } = response.data.data;
        if (accessToken && refreshToken && userName) {
          dispatch(setTokens(accessToken, refreshToken, userName));
          setTimeout(() => {
            window.location.href = '/main';
          }, 100);
        }
      })
      .catch((error: AxiosError) => {
        if (error.response) {
          data = error.response.data;
        }
      });
  }
  return data;
}

export function LogoutAPI(token: string, { dispatch }: LogoutRequest): void {
  const logoutURI = `${AUTH_URI}/logout`;
  if (token) {
    axios
      .post(
        logoutURI,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
      .then(() => {
        dispatch(logout());
        setTimeout(() => {
          window.location.href = '/';
        }, 100);
      })
      .catch(() => {});
  }
}

export async function UsersAPI(token: string) {
  const UsersURI = `${AUTH_URI}/users`;
  if (token) {
    try {
      const response = await axios.get<UsersResponse>(UsersURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data.data;
    } catch (error) {}
  }
}

export async function UserSearchAPI(token: string, keyword: string) {
  const UserSearchURI = `${AUTH_URI}/user/search?keyword=${encodeURIComponent(
    keyword,
  )}`;
  if (token) {
    try {
      const response = await axios.get<UserSerachResponse>(UserSearchURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data.data;
    } catch (error) {}
  }
}
