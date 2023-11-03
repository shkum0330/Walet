import axios from 'axios';
import { NOTICE_URI } from '../apiInfo';
import {
  noticeCreateRequest,
  noticePopCheckResponse,
  noticeUpdateRequest,
  noticelistResponse,
} from '../../interface/api/noticeApiInterface';

export function NoticeListAPI(token: string) {
  const noticeListURI = `${NOTICE_URI}/list`;
  if (token) {
    axios
      .get<noticelistResponse>(noticeListURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}

export function NoticeCreate(token: string, request: noticeCreateRequest) {
  const noticeCreateURI = `${NOTICE_URI}/create`;
  if (token) {
    axios
      .post(noticeCreateURI, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(err => {
        console.log(err);
      });
  }
}

export function NoticeDetail(token: string, id: number) {
  const noticeDetailURI = `${NOTICE_URI}/detail/${id}`;
  if (token) {
    axios
      .get(noticeDetailURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}

export function NoticeUpdate(
  token: string,
  request: noticeUpdateRequest,
  id: number,
) {
  const noticeDetailURI = `${NOTICE_URI}/update/${id}`;
  if (token) {
    axios
      .put(noticeDetailURI, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}

export function NoticeDelete(token: string, id: number) {
  const noticeDeleteURI = `${NOTICE_URI}/delete/${id}`;
  if (token) {
    axios
      .delete(noticeDeleteURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}

export function NoticeSetPop(token: string, id: number) {
  const noticeSetPopURI = `${NOTICE_URI}/pop/${id}`;
  if (token) {
    axios
      .put(
        noticeSetPopURI,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}

export function NoticePopCheck(token: string) {
  const noticePopCheckURI = `${NOTICE_URI}/pop`;
  if (token) {
    axios
      .get<noticePopCheckResponse>(noticePopCheckURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(() => {});
  }
}
