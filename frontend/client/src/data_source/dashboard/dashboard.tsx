import axios from 'axios';
import { ACCOUNT_URI, AUTH_URI, LOG_URI } from '../apiInfo';
import {
  DashboardCategoryesponse,
  DashboardCountResponse,
  DashboardStatsResponse,
  DashboardWeeklyAccountresponse,
  DashboardWeeklyAmountResponse,
  DashboardWeeklyresponse,
} from '../../interface/api/dashboardApiInterface';

export async function DashBoardCountAPI(token: string) {
  const DashBoardCountURI = `${AUTH_URI}/dashboard/count`;
  if (token) {
    try {
      const response = await axios.get<DashboardCountResponse>(
        DashBoardCountURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function DashBoardStatsAPI(token: string) {
  const DashBoardStatsURI = `${LOG_URI}/all`;
  if (token) {
    try {
      const response = await axios.get<DashboardStatsResponse>(
        DashBoardStatsURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function DashBoardWeeklyAPI(token: string) {
  const DashBoardWeeklyURI = `${LOG_URI}/week`;
  if (token) {
    try {
      const response = await axios.get<DashboardWeeklyresponse>(
        DashBoardWeeklyURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function DashBoardWeeklyAccountAPI(token: string) {
  const DashBoardWeeklyAccountURI = `${ACCOUNT_URI}/admin/count/new-account-in-a-week`;
  if (token) {
    try {
      const response = await axios.get<DashboardWeeklyAccountresponse>(
        DashBoardWeeklyAccountURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function DashBoardCategoryAPI(token: string) {
  const DashBoardCategoryURI = `${LOG_URI}/category`;
  if (token) {
    try {
      const response = await axios.get<DashboardCategoryesponse>(
        DashBoardCategoryURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function DashBoardWeeklyAmountAPI(token: string) {
  const DashBoardWeeklyAmountURI = `${LOG_URI}/transaction/amount`;
  if (token) {
    try {
      const response = await axios.get<DashboardWeeklyAmountResponse>(
        DashBoardWeeklyAmountURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}
