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
  days: [];
}

export interface DashboardStatsResponse extends common {
  data: DashboardStats;
}

export interface DashboardWeeklyresponse extends common {
  data: DashboardWeekly;
}

export interface DashboardWeeklyAccountresponse extends common {
  data: string;
}

export interface DashboardCategoryesponse extends common {
  data: {
    category: [];
  };
}

export interface DashboardWeeklyAmountResponse extends common {
  data: string;
}
