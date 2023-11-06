import { useAccessToken } from '../../data_source/apiInfo';
import {
  NoticeCreateAPI,
  NoticeDelete,
  NoticeDetailAPI,
  NoticeListAPI,
  NoticePopCheck,
  NoticeSetPop,
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

export function noticeDeleteRepository(id: number): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeDelete(accessToken, id);
  }
}

export function noticeSetPopRepository(id: number): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeSetPop(accessToken, id);
  }
}

export function noticePopCheckRepository(): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticePopCheck(accessToken);
  }
}
