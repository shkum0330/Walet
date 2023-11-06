import axios from 'axios';
import { NOTICE_URI } from '../apiInfo';
import {
  noticeDetailResponse,
  noticePopCheckResponse,
  noticelistResponse,
} from '../../interface/api/noticeApiInterface';

export async function NoticeListAPI(token: string) {
  const noticeListURI = `${NOTICE_URI}/list`;
  if (token) {
    try {
      const response = await axios.get<noticelistResponse>(noticeListURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data.data.reverse();
    } catch (error) {
      console.error(error);
    }
  }
}

export async function NoticeCreateAPI(token: string, request: FormData) {
  const noticeCreateURI = `${NOTICE_URI}/create`;
  if (token) {
    try {
      const response = await axios.post(noticeCreateURI, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      return true;
    } catch (error) {
      return false;
    }
  }
}

export async function NoticeDetailAPI(token: string, id: string) {
  const noticeDetailURI = `${NOTICE_URI}/detail/${id}`;
  if (token) {
    try {
      const response = await axios.get<noticeDetailResponse>(noticeDetailURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      return response.data.data;
    } catch (error) {
      console.error(error);
    }
  }
}

export async function NoticeUpdateAPI(
  token: string,
  request: FormData,
  id: string,
) {
  const noticeDetailURI = `${NOTICE_URI}/update/${id}`;
  if (token) {
    try {
      const response = await axios.put(noticeDetailURI, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      return true;
    } catch (error) {
      return false;
    }
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
