import { useEffect, useState } from 'react';
import Card from '../components/common/card';
import { noticedata } from '../interface/api/noticeApiInterface';
import { noticeListRepository } from '../repository/notice/noticeRepository';
import NoticeDropdown from '../components/notice/noticeDropdown';

function NoticePage() {
  const [notice, SetNotice] = useState<noticedata[] | null>([]);
  const [selectedItem, setSelectedItem] = useState<noticedata | null>(null);

  useEffect(() => {
    (async () => {
      const data = await noticeListRepository();
      if (data) {
        SetNotice(data);
      }
    })();
  }, []);
  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <p className="text-3xl">공지사항 관리</p>

      <Card width="w-[95%]" height="h-[30%]" styling="p-2 flex flex-col ">
        <div className="flex items-center">
          <p className="text-2xl">배너 미리보기</p>
          <NoticeDropdown />
        </div>

        <div className="w-full h-full flex justify-center">
          <Card width="w-[70%]" height="h-[50%]" styling="p-2 bg-gray-50">
            {selectedItem && (
              <div className="flex justify-around">
                <div>
                  <div className="text-2xl">{selectedItem.title}</div>
                  <div className="text-xl mt-1">{selectedItem.subTitle}</div>
                </div>
                <img
                  src={selectedItem.bannerImg}
                  alt="banner"
                  className="w-[20%] h-[8vh]"
                />
              </div>
            )}
          </Card>
        </div>
      </Card>

      <Card width="w-[95%]" height="h-[50%]" styling="overflow-y-auto">
        <table className="table-fixed w-full ">
          <thead className="bg-gray-200">
            <tr>
              <th className="w-[5%] border border-gray-300">번호</th>
              <th className="w-[15%] border border-gray-300">작성날짜</th>
              <th className="w-[20%] border border-gray-300">제목</th>
              <th className="w-[25%] border border-gray-300">소제목</th>
              <th className="w-[15%] border border-gray-300">활성화 상태</th>
            </tr>
          </thead>
          <tbody className="text-center">
            {notice &&
              notice.map(item => (
                <tr
                  key={item.id}
                  onClick={() => setSelectedItem(item)}
                  className={`cursor-pointer ${
                    selectedItem?.id === item.id ? 'bg-green-100' : ''
                  }`}>
                  <td className="border border-gray-300">{item.id}</td>
                  <td className="border border-gray-300">
                    {(item.registerTime as string).substring(0, 14)}
                  </td>
                  <td className="border border-gray-300">{item.title}</td>
                  <td className="border border-gray-300">{item.subTitle}</td>
                  <td className="border border-gray-300">
                    {item.isActive ? '활성화' : '비활성화'}
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </Card>
    </div>
  );
}

export default NoticePage;
