import axios from 'axios';
import { AUTH_URI } from '../apiInfo';
import { DashboardCountResponse } from '../../interface/api/dashboardApiInterface';

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

export function test() {}
