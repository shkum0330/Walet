import { useEffect, useState } from 'react';
import { TransactionRepository } from '../../repository/member/memberRepository';
import { Accountdata } from '../../interface/api/memberApiInterface';

function TransactionTable({ accountId }: { accountId: string }) {
  const [transaction, SetTransaction] = useState<Accountdata[] | null>([]);

  useEffect(() => {
    (async () => {
      const data = await TransactionRepository(accountId);
      if (data) {
        SetTransaction(data);
      }
    })();
  }, [accountId]);

  return (
    <table className="w-full h-[200px] ">
      <thead>
        <tr className="bg-gray-200">
          <th className="w-[3%] border border-gray-300">계좌명</th>
          <th className="w-[5%] border border-gray-300">거래 타입</th>
          <th className="w-[10%] border border-gray-300">거래 일자</th>
          <th className="w-[10%] border border-gray-300">거래 대상</th>
          <th className="w-[15%] border border-gray-300">금액</th>
        </tr>
      </thead>
      <tbody>
        {transaction && transaction.length > 0 ? (
          transaction.map(items => (
            <tr key={Math.random()}>
              <td className="border border-gray-300  ">{items.accountName}</td>
              <td className="border border-gray-300">
                {items.transactionType}
              </td>
              <td className="border border-gray-300">
                {items.transactionTime.substring(0, 14)}
              </td>
              <td className="border border-gray-300">{items.counterpart}</td>
              <td className="border border-gray-300">{items.paymentAmount}</td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan={5}>거래 데이터가 없습니다</td>
          </tr>
        )}
      </tbody>
    </table>
  );
}

export default TransactionTable;
