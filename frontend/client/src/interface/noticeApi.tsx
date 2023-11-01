import { common } from './commonApi';

export interface noticedata {
  id: string;
  title: string;
  content: string;
  isActive: boolean;
  bannerImg: string;
  registerTime: Date;
  modifyTime: Date;
}

export interface noticelistResponse extends common {
  data: noticedata[];
}

export interface noticeCreateRequest {
  title: string;
  cotnent: string;
  bannerImg: string;
}

export interface noticeDetailResponse extends noticedata {}

export interface noticeUpdateRequest extends noticeCreateRequest {}

export interface noticePopCheckResponse extends noticedata {}
