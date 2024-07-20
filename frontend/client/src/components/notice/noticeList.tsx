import { useEffect, useState } from 'react';
import { noticedata } from '../../interface/api/noticeApiInterface';
import { noticeListRepository } from '../../repository/notice/noticeRepository';

function NoticeList() {
  const [notice, SetNotice] = useState<noticedata[] | null>([]);
  useEffect(() => {
    (async () => {
      const data = await noticeListRepository();
      if (data) {
        SetNotice(data);
      }
    })();
  }, []);
  return (
    <div>
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
              <tr key={item.id}>
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
    </div>
  );
}

export default NoticeList;
