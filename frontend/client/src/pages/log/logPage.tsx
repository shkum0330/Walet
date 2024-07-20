import { useEffect, useState } from 'react';
import Card from '../../components/common/card';
import { Transactionsdata } from '../../interface/api/logApiinterface';
import { TransactionLogAllRepository } from '../../repository/log/logRepository';

function LogPage() {
  const [logdata, SetLogdata] = useState<Transactionsdata[]>();

  useEffect(() => {
    (async () => {
      const data = await TransactionLogAllRepository();
      if (data) {
        SetLogdata(data.transactions);
      }
    })();
  }, []);

  const getTransactionType = (transactionType: string) => {
    switch (transactionType) {
      case 'DEPOSIT':
        return '입금';
      case 'WITHDRAWAL':
        return '출금';
      case 'TRANSFER':
        return '양도';
      default:
        return '';
    }
  };

  const getTransactionTypeStyle = (transactionType: string) => {
    switch (transactionType) {
      case 'DEPOSIT':
        return 'text-green-500';
      case 'WITHDRAWAL':
        return 'text-red-500';
      case 'TRANSFER':
        return 'text-blue-500';
      default:
        return 'text-black';
    }
  };

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">로그 관리</p>
      </div>
      <Card width="w-[95%]" height="h-[90%]" styling="overflow-y-auto">
        <table className="table-fixed w-full ">
          <thead className="bg-gray-200">
            <tr>
              <th className="w-[15%] border border-gray-300">시간</th>
              <th className="w-[10%] border border-gray-300">거래타입</th>
              <th className="w-[10%] border border-gray-300">거래자</th>
              <th className="w-[25%] border border-gray-300">거래금액</th>
            </tr>
          </thead>
          <tbody className="text-center">
            {logdata &&
              logdata.map(item => (
                <tr key={item.id}>
                  <td className="border border-gray-300">
                    {item.transactionTime}
                  </td>
                  <td
                    className={`border border-gray-300 ${getTransactionTypeStyle(
                      item.transactionType,
                    )}`}>
                    {getTransactionType(item.transactionType)}
                  </td>
                  <td className="border border-gray-300">{item.recipient}</td>

                  <td className="border border-gray-300">
                    {item.paymentAmount}원
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </Card>
    </div>
  );
}

export default LogPage;
