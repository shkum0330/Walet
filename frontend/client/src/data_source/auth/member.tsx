import axios, { AxiosError } from 'axios';
import { ACCOUNT_URI, AUTH_URI } from '../apiInfo';
import {
  LogoutRequest,
  LoginResponse,
  LoginRequest,
  UsersResponse,
  UserSerachResponse,
  UserResponse,
  TransactionResponse,
} from '../../interface/api/memberApiInterface';
import { logout } from '../../store/actions/authActions';
import { setTokens } from '../../store/store';
import { ErrorCommon } from '../../interface/api/commonApiInterface';

export async function LoginAPI({ email, password, dispatch }: LoginRequest) {
  const loginURI = `${AUTH_URI}/admin/login`;
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
        const errorData = error.response?.data as ErrorCommon;
        if (errorData.message) {
          data = errorData.message;
        } else {
          data = '잠시 후 다시 시도해주세요.';
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

export async function UserAPI(token: string) {
  const UsersURI = `${AUTH_URI}/user`;
  if (token) {
    try {
      const response = await axios.get<UserResponse>(UsersURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data.data;
    } catch (error) {}
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

export async function TransactionAPI(token: string, id: string) {
  const UserSearchURI = `${ACCOUNT_URI}/admin/transaction/${id}`;
  if (token) {
    try {
      const response = await axios.get<TransactionResponse>(UserSearchURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data.data;
    } catch (error) {}
  }
}

export async function UserReviseAPI(token: string, id: string) {
  const UserRevisehURI = `${AUTH_URI}/admin/user/${id}`;
  if (token) {
    try {
      const response = await axios.post(
        UserRevisehURI,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return null;
    } catch (error) {}
  }
}
