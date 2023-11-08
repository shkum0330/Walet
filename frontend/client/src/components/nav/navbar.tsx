import { Outlet, useLocation } from 'react-router-dom';
import Homeicon from '../Icons/homeicon';
import LogIcon from '../Icons/logicon';
import NoticeIcon from '../Icons/noticeicon';
import UserIcon from '../Icons/usericon';
import Logo from '../../assets/imgs/Logo.png';
import { useUsername } from '../../data_source/apiInfo';

function Navbar() {
  const location = useLocation();
  const name = useUsername();
  return (
    <>
      <nav className="flex items-center py-4 px-6 text-xl shadow-md w-full">
        <img src={Logo} alt="Logo" className="w-[60px] h-[30px] mr-4" />
        <p className="flex items-center space-x-4 font-do-hyeon">
          환영합니다! 관리자 {name} 님!
        </p>
      </nav>
      <aside
        id="default-sidebar"
        className="fixed top-0 left-0 z-40 w-24 h-screen transition-transform -translate-x-full sm:translate-x-0"
        aria-label="Sidebar">
        <div className="h-full px-3 py-4 mt-16 bg-gray-100 dark:bg-gray-800">
          <a
            href="/main"
            className={`flex flex-col justify-center items-center my-4 ${
              location.pathname.includes('/main')
                ? 'text-green-500'
                : 'text-gray-500'
            }`}>
            <Homeicon styling="w-8 h-8" />
            <p>홈</p>
          </a>
          <a
            href="/member"
            className={`flex flex-col justify-center items-center my-4 ${
              location.pathname.includes('/member')
                ? 'text-green-500'
                : 'text-gray-500'
            }`}>
            <UserIcon styling="w-8 h-8" />
            <p>회원관리</p>
          </a>
          <a
            href="/log"
            className={`flex flex-col justify-center items-center my-4 ${
              location.pathname.includes('/log')
                ? 'text-green-500'
                : 'text-gray-500'
            }`}>
            <LogIcon styling="w-8 h-8" />
            <p>거래로그</p>
          </a>
          <a
            href="/notice"
            className={`flex flex-col justify-center items-center my-4 ${
              location.pathname.includes('/notice')
                ? 'text-green-500'
                : 'text-gray-500'
            }`}>
            <NoticeIcon styling="w-8 h-8" />
            <p>공지사항</p>
          </a>
        </div>
      </aside>
      <Outlet />
    </>
  );
}

export default Navbar;
