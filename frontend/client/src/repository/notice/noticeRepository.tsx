import { useAccessToken } from '../../data_source/apiInfo';
import {
  NoticeCreateAPI,
  NoticeDeleteAPI,
  NoticeDetailAPI,
  NoticeListAPI,
  NoticePopCheck,
  NoticeSetPopAPI,
  NoticeUpdateAPI,
} from '../../data_source/notice/notice';

export async function noticeListRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticeListAPI(accessToken);
    return data;
  }
  return null;
}

export async function noticeCreateRepository(request: FormData) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticeCreateAPI(accessToken, request);
    return data;
  }
  return null;
}

export async function noticeDetailRepository(id: string) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticeDetailAPI(accessToken, id);
    return data;
  }
  return null;
}

export async function noticeUpdateRepository(request: FormData, id: string) {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticeUpdateAPI(accessToken, request, id);
    return data;
  }
  return null;
}

export function noticeDeleteRepository(id: string): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeDeleteAPI(accessToken, id);
  }
}

export function noticeSetPopRepository(id: string): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeSetPopAPI(accessToken, id);
  }
}

export async function noticePopCheckRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticePopCheck(accessToken);
    return data;
  }
  return null;
}
