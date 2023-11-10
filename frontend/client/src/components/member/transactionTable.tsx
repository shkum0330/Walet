import { useEffect, useState } from 'react';
import { noticedata } from '../../interface/api/noticeApiInterface';
import { noticeListRepository } from '../../repository/notice/noticeRepository';

function TransactionTable({ accountId }: { accountId: number }) {
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
    <table className="w-full h-[200px] ">
      <thead>
        <tr className="bg-gray-200">
          <th className="w-[3%] border border-gray-300">계좌명</th>
          <th className="w-[5%] border border-gray-300">거래 타입</th>
          <th className="w-[10%] border border-gray-300">거래 일자</th>
          <th className="w-[10%] border border-gray-300">거래 대상</th>
          <th className="w-[15%] border border-gray-300">가입 금액</th>
        </tr>
      </thead>
      <thead className=" ">
        {notice?.map(items => (
          <tr key={items.id}>
            <td
              className={`border border-gray-300  ${
                items.isActive ? 'text-red-500' : ''
              } `}>
              {items.isActive ? 'V' : ''}
            </td>
            <td className="border border-gray-300">{items.id}</td>
            <td className="border border-gray-300">
              {items.registerTime.substring(0, 14)}
            </td>
            <td className="border border-gray-300">{items.title}</td>
            <td className="border border-gray-300">{items.subTitle}</td>
          </tr>
        ))}
      </thead>
    </table>
  );
}

export default TransactionTable;
