import { useAccessToken } from '../../data_source/apiInfo';
import {
  NoticeCreate,
  NoticeDelete,
  NoticeDetail,
  NoticeListAPI,
  NoticePopCheck,
  NoticeSetPop,
  NoticeUpdate,
} from '../../data_source/notice/notice';
import {
  noticeCreateRequest,
  noticeUpdateRequest,
} from '../../interface/api/noticeApiInterface';

export async function noticeListRepository() {
  const accessToken = useAccessToken();
  if (accessToken) {
    const data = await NoticeListAPI(accessToken);
    return data;
  }
  return null;
}

export function noticeCreateRepository(request: noticeCreateRequest): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeCreate(accessToken, request);
  }
}

export function noticeDetailRepository(id: number): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeDetail(accessToken, id);
  }
}

export function noticeUpdateRepository(
  request: noticeUpdateRequest,
  id: number,
): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    NoticeUpdate(accessToken, request, id);
  }
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
