import { common } from './commonApiInterface';

export interface DashboardCountResponse extends common {
  data: DashboardCountData;
}

export interface DashboardCountData {
  newUser: string;
  allUsers: number;
}
