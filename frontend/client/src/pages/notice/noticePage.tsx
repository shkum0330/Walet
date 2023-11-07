import { useEffect, useState } from 'react';
import Card from '../../components/common/card';
import { noticedata } from '../../interface/api/noticeApiInterface';
import {
  noticeListRepository,
  noticePopCheckRepository,
} from '../../repository/notice/noticeRepository';

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
    (async () => {
      const data = await noticePopCheckRepository();
      if (data) {
        setSelectedItem(data);
      }
    })();
  }, []);

  const handleDetail = (id: string) => {
    window.location.href = `/notice/${id}`;
  };

  const handleCreate = () => {
    window.location.href = '/notice/create';
  };

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">공지사항 관리</p>
        <button
          type="button"
          className="ml-4 p-2 bg-gray-300 rounded-lg"
          onClick={handleCreate}>
          작성하기
        </button>
      </div>

      <Card width="w-[95%]" height="h-[30%]" styling="p-2 flex flex-col ">
        <div className="flex items-center">
          <p className="text-2xl">배너 미리보기</p>
        </div>

        <div className="w-full h-full flex justify-center">
          <Card width="w-[30%]" height="h-[50%]" styling="p-2 bg-gray-50">
            {selectedItem && (
              <div className="flex justify-around">
                <div>
                  <div className="text-2xl mt-2">{selectedItem.title}</div>
                  <div className="text-xl mt-1">{selectedItem.subTitle}</div>
                </div>
                <img
                  src={selectedItem.bannerImg}
                  alt="banner"
                  className="w-[40%] h-[8vh]"
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
              <th className="w-[4%] border border-gray-300">활성화</th>
              <th className="w-[5%] border border-gray-300">번호</th>
              <th className="w-[15%] border border-gray-300">작성날짜</th>
              <th className="w-[20%] border border-gray-300">제목</th>
              <th className="w-[25%] border border-gray-300">소제목</th>
              <th className="w-[15%] border border-gray-300">내용</th>
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
                  } `}>
                  <td
                    className={`border border-gray-300  ${
                      item.isActive ? 'text-red-500' : ''
                    } `}>
                    {item.isActive ? 'V' : ''}
                  </td>
                  <td className="border border-gray-300">{item.id}</td>
                  <td className="border border-gray-300">
                    {item.registerTime.substring(0, 14)}
                  </td>
                  <td className="border border-gray-300">{item.title}</td>
                  <td className="border border-gray-300">{item.subTitle}</td>
                  <td className="border border-gray-300">
                    <button type="button" onClick={() => handleDetail(item.id)}>
                      상세보기
                    </button>
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
