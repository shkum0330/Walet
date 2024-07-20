import { useEffect, useState } from 'react';
import Card from '../common/card';
import {
  noticeListRepository,
  noticePopCheckRepository,
} from '../../repository/notice/noticeRepository';
import { noticedata } from '../../interface/api/noticeApiInterface';

function NoticeManage() {
  const handleDetail = (id: string) => {
    window.location.href = `notice/${id}`;
  };
  const [notice, SetNotice] = useState<noticedata[] | null>([]);
  const [popNotice, setPopNotice] = useState<noticedata | null>(null);

  useEffect(() => {
    (async () => {
      const data = await noticeListRepository();
      if (data) {
        SetNotice(data);
      }
    })();
    (async () => {
      const data = await noticePopCheckRepository();
      if (data) {
        setPopNotice(data);
      }
    })();
  }, []);

  return (
    <>
      <p>공지사항 관리</p>
      <Card
        width="w-[80%]"
        height="h-[23%]"
        styling="p-2 bg-gray-50 ring-2 ring-green-400">
        <div
          onClick={() => {
            handleDetail(popNotice?.id as string);
          }}
          onKeyPress={() => {
            handleDetail(popNotice?.id as string);
          }}
          role="button"
          tabIndex={0}
          className="flex justify-around">
          <div>
            <div className="text-sm font-bold ">{popNotice?.title}</div>
            <div className="text-xs ">{popNotice?.subTitle}</div>
          </div>
          <img
            src={popNotice?.bannerImg}
            alt="banner"
            className="w-[25%] h-[4vh]"
          />
        </div>
      </Card>
      {notice &&
        notice.slice(0, 2).map(item => (
          <Card
            key={item.id}
            width="w-[80%]"
            height="h-[23%]"
            styling="p-2 bg-gray-50">
            <div
              onClick={() => {
                handleDetail(item.id);
              }}
              onKeyPress={() => {
                handleDetail(item.id);
              }}
              role="button"
              tabIndex={0}
              className="flex justify-around">
              <div>
                <div className="text-sm font-bold">{item.title}</div>
                <div className="text-xs">{item.subTitle}</div>
              </div>
              <img
                src={item.bannerImg}
                alt="banner"
                className="w-[25%] h-[4vh]"
              />
            </div>
          </Card>
        ))}
    </>
  );
}

export default NoticeManage;
