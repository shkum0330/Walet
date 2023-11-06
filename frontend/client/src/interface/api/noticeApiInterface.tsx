import { common } from './commonApiInterface';

export interface noticedata {
  id: string;
  title: string;
  subTitle: string;
  content: string;
  isActive: boolean;
  bannerImg: string;
  registerTime: string;
  modifyTime: string;
}

export interface noticelistResponse extends common {
  data: noticedata[];
}

export interface noticelistResponse extends common {}

export interface noticeCreateRequest {
  title: string;
  subTitle: string;
  cotnent: string;
  bannerImg: string;
}

export interface noticeDetailResponse extends common {
  data: noticedata;
}

export interface noticeUpdateRequest extends noticeCreateRequest {}

export interface noticePopCheckResponse extends noticedata {}
