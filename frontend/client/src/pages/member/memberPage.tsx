import { useEffect, useState } from 'react';
import Card from '../../components/common/card';
import { Usersdata } from '../../interface/api/memberApiInterface';
import { UserSearchRepository } from '../../repository/member/memberRepository';
import LeftIcon from '../../components/Icons/lefticon';
import RightIcon from '../../components/Icons/righticon';
import SearchIcon from '../../components/Icons/searchicon';

function MemberPage() {
  const [users, SetUsers] = useState<Usersdata[] | null>([]);
  const [keyword, setKeyword] = useState<string>('');
  useEffect(() => {
    (async () => {
      const data = await UserSearchRepository(keyword);
      if (data) {
        SetUsers(data);
      }
    })();
  }, [keyword]);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const totalItems = users?.length || 0;
  const totalPages = Math.ceil(totalItems / 20);

  const next = () => {
    if (currentPage === totalPages) return;
    setCurrentPage(currentPage + 1);
  };

  const prev = () => {
    if (currentPage === 1) return;
    setCurrentPage(currentPage - 1);
  };

  const getPageNumbers = () => {
    const pageNumbers = [];
    const middlePage = Math.ceil(currentPage / 5) * 5;
    const startPage = Math.max(1, middlePage - 4);
    const endPage = Math.min(totalPages, middlePage);

    for (let i = startPage; i <= endPage; i += 1) {
      pageNumbers.push(i);
    }

    return pageNumbers;
  };

  const startIndex = (currentPage - 1) * 20;
  const endIndex = Math.min(startIndex + 20, totalItems);

  const handleDetail = (id: string) => {
    window.location.href = `/member/${id}`;
  };

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">회원 관리</p>
      </div>

      <Card width="w-[95%]" height="h-[15%]" styling="p-2 flex flex-col ">
        <div className="flex items-center">
          <p className="text-2xl">회원 검색</p>
        </div>
        <div className="w-full flex">
          <Card width="w-full" height="h-[60%]" styling="mb-4 bg-gray-50">
            <div className="flex bg-MAIN-100 rounded-2xl p-1 mt-2">
              <SearchIcon styling="w-6 h-6 ml-2" />
              <input
                className="bg-transparent ml-4 w-full border-none outline-none"
                placeholder="사용자명을 입력하세요."
                onChange={event => {
                  setKeyword(event.target.value);
                }}
              />
            </div>
          </Card>
        </div>
      </Card>

      <Card width="w-[95%]" height="h-[70%]" styling="">
        <table
          className={`table-fixed w-full ${
            users && users.slice(startIndex, endIndex).length === 20
              ? 'h-full'
              : ''
          }`}>
          <thead className="bg-gray-200">
            <tr className="">
              <th className="w-[3%] border border-gray-300">번호</th>
              <th className="w-[5%] border border-gray-300">이름</th>
              <th className="w-[10%] border border-gray-300">연락처</th>
              <th className="w-[10%] border border-gray-300">이메일</th>
              <th className="w-[15%] border border-gray-300">가입일</th>
              <th className="w-[15%] border border-gray-300">통장잔액</th>
              <th className="w-[5%] border border-gray-300">상세</th>
            </tr>
          </thead>
          <tbody className="text-center">
            {users &&
              users.slice(startIndex, endIndex).map(item => (
                <tr key={item.id}>
                  <td className="border border-gray-300">{item.id}</td>
                  <td className="border border-gray-300">{item.name}</td>
                  <td className="border border-gray-300">{item.phoneNumber}</td>
                  <td className="border border-gray-300">{item.email}</td>
                  <td className="border border-gray-300">
                    {item.createdDate.substring(0, 14)}
                  </td>
                  <td className="border border-gray-300">999,999,999원</td>
                  <td className="border border-gray-300 ">
                    <button type="button" onClick={() => handleDetail(item.id)}>
                      상세보기
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </Card>
      <div className="flex justify-center mt-4">
        <button type="button" onClick={prev} disabled={currentPage === 1}>
          <LeftIcon styling="w-6 h-6" />
        </button>
        {getPageNumbers().map(pageNumber => (
          <button
            onClick={() => setCurrentPage(pageNumber)}
            className={`mx-2 p-2  ${
              currentPage === pageNumber ? 'bg-gray-200 rounded-full w-10' : ''
            }`}
            key={pageNumber}
            type="button">
            {pageNumber}
          </button>
        ))}
        <button
          type="button"
          onClick={next}
          disabled={currentPage === totalPages}>
          <RightIcon styling="w-6 h-6" />
        </button>
      </div>
    </div>
  );
}

export default MemberPage;
