import { useAccessToken } from '../../data_source/apiInfo';
import {
  DashBoardCategoryAPI,
  DashBoardCountAPI,
  DashBoardStatsAPI,
  DashBoardWeeklyAPI,
  DashBoardWeeklyAccountAPI,
  DashBoardWeeklyAmountAPI,
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

export async function DashBoardWeeklyAccountRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardWeeklyAccountAPI(accessToken);
    return data;
  }
  return null;
}

export async function DashBoardCategoryRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardCategoryAPI(accessToken);
    return data;
  }
  return null;
}

export async function DashBoardWeeklyAmountRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await DashBoardWeeklyAmountAPI(accessToken);
    return data;
  }
  return null;
}
