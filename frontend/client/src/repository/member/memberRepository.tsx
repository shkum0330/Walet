import { useAccessToken } from '../../data_source/apiInfo';
import { LoginAPI, LogoutAPI, UsersAPI } from '../../data_source/auth/member';
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

export function UsersRepository(): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    UsersAPI(accessToken);
  }
}
