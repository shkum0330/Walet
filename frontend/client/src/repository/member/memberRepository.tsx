import { useAccessToken } from '../../data_source/apiInfo';
import {
  LoginAPI,
  LogoutAPI,
  TransactionAPI,
  UserAPI,
  UserReviseAPI,
  UserSearchAPI,
  UsersAPI,
} from '../../data_source/auth/member';
import {
  LogoutRequest,
  LoginRequest,
} from '../../interface/api/memberApiInterface';

export async function LoginRepository(request: LoginRequest) {
  const data = await LoginAPI(request);
  return data;
}

export function LogoutRepository(request: LogoutRequest): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    LogoutAPI(accessToken, request);
  }
}

export async function UserRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await UserAPI(accessToken);
    return data;
  }
  return null;
}

export async function UsersRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await UsersAPI(accessToken);
    return data;
  }
  return null;
}

export async function UserSearchRepository(keyword: string) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await UserSearchAPI(accessToken, keyword);
    return data;
  }
  return null;
}

export async function TransactionRepository(id: string) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await TransactionAPI(accessToken, id);
    return data;
  }
  return null;
}

export async function UserReviseRepository(id: string) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await UserReviseAPI(accessToken, id);
    return data;
  }
  return null;
}
