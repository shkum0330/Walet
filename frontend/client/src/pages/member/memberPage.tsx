import React, { useEffect, useState } from 'react';
import { Transition } from '@headlessui/react';
import Card from '../../components/common/card';
import { Userdata } from '../../interface/api/memberApiInterface';
import { UserSearchRepository } from '../../repository/member/memberRepository';
import LeftIcon from '../../components/Icons/lefticon';
import RightIcon from '../../components/Icons/righticon';
import SearchIcon from '../../components/Icons/searchicon';
import { noticeListRepository } from '../../repository/notice/noticeRepository';
import { noticedata } from '../../interface/api/noticeApiInterface';

function MemberPage() {
  const [users, SetUsers] = useState<Userdata[] | null>([]);
  const [selectedUser, setSelectedUser] = useState<string | null>(null);
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

  const handleRowClick = (id: string) => {
    setSelectedUser(prevId => (prevId === id ? null : id));
  };

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

      <Card width="w-[95%]" height="h-auto" styling="">
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
              <th className="w-[15%] border border-gray-300">보유계좌</th>
              <th className="w-[5%] border border-gray-300">비고</th>
            </tr>
          </thead>
          <tbody className="text-center ">
            {users &&
              users.slice(startIndex, endIndex).map(item => (
                <React.Fragment key={item.id}>
                  <tr
                    className={selectedUser === item.id ? 'bg-green-100' : ''}
                    onClick={() => handleRowClick(item.id)}>
                    <td className="border border-gray-300">{item.id}</td>
                    <td className="border border-gray-300">{item.name}</td>
                    <td className="border border-gray-300">
                      {item.phoneNumber}
                    </td>
                    <td className="border border-gray-300">{item.email}</td>
                    <td className="border border-gray-300">
                      {item.createdDate.substring(0, 14)}
                    </td>
                    <td className="border border-gray-300">999,999,999원</td>
                    <td className="border border-gray-300 ">-</td>
                  </tr>
                  <Transition
                    show={selectedUser === item.id}
                    as="tr"
                    enter="transition ease-out duration-100"
                    enterFrom="transform opacity-0 scale-95"
                    enterTo="transform opacity-100 scale-100"
                    leave="transition ease-in duration-75"
                    leaveFrom="transform opacity-100 scale-100"
                    leaveTo="transform opacity-0 scale-95">
                    {selectedUser === item.id && (
                      <td colSpan={7}>
                        <div className="h-[500px] overflow-y-auto">
                          <table className="w-full h-[200px] ">
                            <thead>
                              <tr className="bg-gray-200">
                                <th className="w-[3%] border border-gray-300">
                                  계좌명
                                </th>
                                <th className="w-[5%] border border-gray-300">
                                  거래 타입
                                </th>
                                <th className="w-[10%] border border-gray-300">
                                  거래 일자
                                </th>
                                <th className="w-[10%] border border-gray-300">
                                  거래 대상
                                </th>
                                <th className="w-[15%] border border-gray-300">
                                  가입 금액
                                </th>
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
                                  <td className="border border-gray-300">
                                    {items.id}
                                  </td>
                                  <td className="border border-gray-300">
                                    {items.registerTime.substring(0, 14)}
                                  </td>
                                  <td className="border border-gray-300">
                                    {items.title}
                                  </td>
                                  <td className="border border-gray-300">
                                    {items.subTitle}
                                  </td>
                                </tr>
                              ))}
                            </thead>
                          </table>
                        </div>
                      </td>
                    )}
                  </Transition>
                </React.Fragment>
              ))}
          </tbody>
        </table>
      </Card>
      <div className="flex justify-center mt-4">
        <button
          type="button"
          onClick={prev}
          disabled={currentPage === 1}
          aria-label="이전 페이지">
          <LeftIcon styling="w-6 h-6" />
        </button>
        {getPageNumbers().map(pageNumber => (
          <button
            onClick={() => setCurrentPage(pageNumber)}
            className={`mx-2 p-2  ${
              currentPage === pageNumber ? 'bg-gray-200 rounded-full w-10' : ''
            }`}
            key={pageNumber}
            type="button"
            aria-label={`페이지 ${pageNumber}로 이동`}>
            {pageNumber}
          </button>
        ))}
        <button
          type="button"
          onClick={next}
          disabled={currentPage === totalPages}
          aria-label="다음 페이지">
          <RightIcon styling="w-6 h-6" />
        </button>
      </div>
    </div>
  );
}

export default MemberPage;
