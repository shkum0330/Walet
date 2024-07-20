const BASE_URI = '';
const NOTICE_URI = `${BASE_URI}/api/notice`;
const AUTH_URI = `${BASE_URI}/api/auth`;
const ACCOUNT_URI = `${BASE_URI}/api/account`;
const LOG_URI = `${BASE_URI}/api/log`;

// const BASES_URI = 'http://localhost'; // 테스트용
// const ACCOUNT_URI = `${BASE_URI}:8082`; // 테스트용
// const NOTICE_URI = `${BASE_URI}:8083`; // 테스트용
// const AUTH_URI = `${BASES_URI}:8084`; // 테스트용
// const LOG_URI = `${BASE_URI}:8084/api/auth`;

function useAccessToken(): string | null {
  const storageValue = sessionStorage.getItem('persist:root') as string;
  if (storageValue) {
    const storageObject = JSON.parse(storageValue) as object;
    const authString = (storageObject as { auth: string }).auth;
    const authObject = JSON.parse(authString) as object;
    const token = (authObject as { accessToken: string }).accessToken;
    return token;
  }
  return null;
}

function useRefreshToken(): string | null {
  const storageValue = sessionStorage.getItem('persist:root') as string;
  if (storageValue) {
    const storageObject = JSON.parse(storageValue) as object;
    const authString = (storageObject as { auth: string }).auth;
    const authObject = JSON.parse(authString) as object;
    const token = (authObject as { refreshToken: string }).refreshToken;
    return token;
  }
  return null;
}

function useUsername(): string | null {
  const storageValue = sessionStorage.getItem('persist:root') as string;
  if (storageValue) {
    const storageObject = JSON.parse(storageValue) as object;
    const authString = (storageObject as { auth: string }).auth;
    const authObject = JSON.parse(authString) as object;
    const name = (authObject as { userName: string }).userName;
    return name;
  }
  return null;
}

export {
  AUTH_URI,
  ACCOUNT_URI,
  NOTICE_URI,
  LOG_URI,
  useAccessToken,
  useRefreshToken,
  useUsername,
};
