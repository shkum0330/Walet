import { useAccessToken } from '../../data_source/apiInfo';
import {
  TransactionLogAPI,
  TransactionLogAllAPI,
} from '../../data_source/log/log';

export async function TransactionLogAllRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await TransactionLogAllAPI(accessToken);
    return data;
  }
  return null;
}

export async function TransactionLogRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await TransactionLogAPI(accessToken);
    return data;
  }
  return null;
}
