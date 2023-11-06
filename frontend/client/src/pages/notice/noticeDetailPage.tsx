import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Card from '../../components/common/card';
import NoticeDropdown from '../../components/notice/noticeDropdown';
import { noticedata } from '../../interface/api/noticeApiInterface';
import { noticeDetailRepository } from '../../repository/notice/noticeRepository';

function NoticeDetailPage() {
  const { id } = useParams();
  const [notice, SetNotice] = useState<noticedata | null>();

  useEffect(() => {
    if (id) {
      (async () => {
        const data = await noticeDetailRepository(id);
        if (data) {
          SetNotice(data);
        }
      })();
    }
  }, []);

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">공지사항 상세보기</p>
        <NoticeDropdown />
      </div>
      <div className="flex w-full h-full">
        <div className="w-[50%]">
          <Card width="w-[95%]" height="h-[30%]" styling="p-2 flex flex-col ">
            <div className="flex items-center">
              <p className="text-2xl">배너 미리보기</p>
            </div>

            <div className="w-full h-full flex justify-center">
              <Card width="w-[70%]" height="h-[50%]" styling="p-2 bg-gray-50">
                <div className="flex justify-around">
                  <div>
                    <div className="text-2xl mt-2">{notice?.title}</div>
                    <div className="text-xl mt-1">{notice?.subTitle}</div>
                  </div>
                  <img
                    src={notice?.bannerImg}
                    alt="banner"
                    className="w-[40%] h-[8vh]"
                  />
                </div>
              </Card>
            </div>
          </Card>

          <Card width="w-[95%]" height="h-[50%]" styling="overflow-y-auto">
            <p className="text-2xl">작성 상세 내용</p>
            <div className="text-2xl mt-2"> {notice?.title}</div>
            <div className="text-xl mt-1"> {notice?.subTitle}</div>
            <div className="mt-2">{notice?.content}</div>
          </Card>
        </div>

        <Card width="w-[40%]" height="h-[84%]" styling="">
          <p className="text-2xl ml-2 mt-2">상세 화면 미리보기</p>
          <div className="w-full h-full flex justify-center">
            <Card
              width="w-[360px]"
              height="h-[800px]"
              styling=" bg-gray-50 overflow-y-auto scrollbar-hide">
              <img
                src={notice?.bannerImg}
                alt="banner"
                className="w-full h-[30%] bg-[#FFEB7A]"
              />
              <p className="text-2xl text-center mt-8">{notice?.title}</p>
              <p className="text-lx text-center mt-1">{notice?.subTitle}</p>
              <div className="h-full">
                <p className="mt-3"> {notice?.content}</p>
              </div>
            </Card>
          </div>
        </Card>
      </div>
    </div>
  );
}

export default NoticeDetailPage;
