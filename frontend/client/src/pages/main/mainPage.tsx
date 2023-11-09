import { useEffect, useState } from 'react';
import PetsIcon from '@mui/icons-material/Pets';
import Card from '../../components/common/card';
import { DashboardCountData } from '../../interface/api/dashboardApiInterface';
import { DashBoardCountRepository } from '../../repository/dashboard/dashboardRepository';
import UserIcon from '../../components/Icons/usericon';
import AccountIcon from '../../components/Icons/accounticon';
import { noticedata } from '../../interface/api/noticeApiInterface';
import {
  noticeListRepository,
  noticePopCheckRepository,
} from '../../repository/notice/noticeRepository';

function MainPage() {
  const [countData, SetCountData] = useState<DashboardCountData>();
  const [notice, SetNotice] = useState<noticedata[] | null>([]);
  const [popNotice, setPopNotice] = useState<noticedata | null>(null);

  useEffect(() => {
    (async () => {
      const data = await DashBoardCountRepository();
      if (data) {
        SetCountData(data);
      }
    })();
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
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <p className="text-3xl font-do-hyeon">DashBoard</p>
      <div className="flex h-[12%]">
        <Card width="w-[22%]" height="h-full" styling="p-2">
          신규 계좌 발급
        </Card>
        <Card width="w-[47%]" height="h-full" styling="p-2">
          주간 신규 고객 현황
          <p className="text-3xl ml-2 mt-2">{countData?.newUser}명 </p>
        </Card>
        <Card width="w-[22%]" height="h-full" styling="p-2">
          총 거래량
        </Card>
      </div>
      <div className="flex mt-8 h-[38%]">
        <Card width="w-[22%]" height="h-full" styling="p-2">
          <p>시스템 개요</p>
          <div className="flex ml-8 mt-4 h-[20%] align-middle">
            <div className="w-[70%]">
              <p className="text-3xl">{countData?.allUsers}</p>
              <p>총 고객 수</p>
            </div>
            <UserIcon styling="w-14 h-14" />
          </div>
          <hr className="mt-4 border-2" />
          <div className="flex ml-8 mt-4 h-[20%] align-middle">
            <div className="w-[70%]">
              <p className="text-3xl">{countData?.allUsers}</p>
              <p>일반 계좌 수</p>
            </div>
            <AccountIcon styling="w-14 h-14" />
          </div>
          <hr className="mt-4 border-2" />
          <div className="flex ml-8 mt-4 h-[20%] align-middle">
            <div className="w-[70%]">
              <p className="text-3xl">{countData?.allUsers}</p>
              <p>반려 동물 계좌 수</p>
            </div>
            <PetsIcon style={{ fontSize: 70 }} />
          </div>
        </Card>
        <Card width="w-[47%]" height="h-full" styling="p-2">
          기간 거래금액 그래프
        </Card>
        <Card width="w-[22%]" height="h-full" styling="p-2">
          기간 거래량 분석
        </Card>
      </div>
      <div className="flex mt-8 h-[28%]">
        <Card width="w-[22%]" height="h-full" styling="p-2">
          카테고리별 매출
        </Card>
        <Card width="w-[47%]" height="h-full" styling="p-2">
          최근 거래 내역
        </Card>
        <Card width="w-[22%]" height="h-full" styling="p-2">
          공지사항 관리
          <Card
            width="w-[80%]"
            height="h-[23%]"
            styling="p-2 bg-gray-50 ring-2 ring-green-400">
            <div className="flex justify-around">
              <div>
                <div className="text-base ">{popNotice?.title}</div>
                <div className="text-sm ">{popNotice?.subTitle}</div>
              </div>
              <img
                src={popNotice?.bannerImg}
                alt="banner"
                className="w-[30%] h-[4vh]"
              />
            </div>
          </Card>
          {notice &&
            notice.slice(0, 2).map(item => (
              <Card width="w-[80%]" height="h-[23%]" styling="p-2 bg-gray-50">
                <div className="flex justify-around">
                  <div>
                    <div className="text-base ">{item.title}</div>
                    <div className="text-sm ">{item.subTitle}</div>
                  </div>
                  <img
                    src={item.bannerImg}
                    alt="banner"
                    className="w-[30%] h-[4vh]"
                  />
                </div>
              </Card>
            ))}
        </Card>
      </div>
    </div>
  );
}

export default MainPage;
