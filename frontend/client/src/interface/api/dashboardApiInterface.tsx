import { common } from './commonApiInterface';

export interface DashboardCountResponse extends common {
  data: DashboardCountData;
}

export interface DashboardCountData {
  newUser: string;
  allUsers: number;
  generalAccountCount: number;
  petAccountCount: number;
}

export interface DashboardStats {
  day: [];
  week: [];
  month: [];
}

export interface DashboardWeekly {
  day: [];
}

export interface DashboardStatsResponse extends common {
  data: DashboardStats;
}

export interface DashboardWeeklyesponse extends common {
  data: DashboardWeekly;
}
