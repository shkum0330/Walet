import { useAccessToken } from '../../data_source/apiInfo';
import { DashBoardCountAPI } from '../../data_source/dashboard/dashboard';

export async function DashBoardCountRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardCountAPI(accessToken);
    return data;
  }
  return null;
}

export function test() {
  const accessToken = useAccessToken();
  if (accessToken) {
  }
  return null;
}
