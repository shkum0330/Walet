import axios from 'axios';
import { AUTH_URI, LOG_URI } from '../apiInfo';
import {
  DashboardCountResponse,
  DashboardStatsResponse,
  DashboardWeeklyesponse,
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
      const response = await axios.get<DashboardWeeklyesponse>(
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
