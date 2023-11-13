import { useAccessToken } from '../../data_source/apiInfo';
import {
  DashBoardCountAPI,
  DashBoardStatsAPI,
  DashBoardWeeklyAPI,
} from '../../data_source/dashboard/dashboard';

export async function DashBoardCountRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardCountAPI(accessToken);
    return data;
  }
  return null;
}

export async function DashBoardStatsRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardStatsAPI(accessToken);
    return data;
  }
  return null;
}

export async function DashBoardWeeklyRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardWeeklyAPI(accessToken);
    return data;
  }
  return null;
}
