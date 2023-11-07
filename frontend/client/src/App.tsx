import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AxiosInterceptor } from './repository/axios/instanceRepository';
import LoginPage from './pages/loginPage';
import MainPage from './pages/mainPage';
import Navbar from './components/nav/navbar';
// 공지사항 페이지
import NoticePage from './pages/notice/noticePage';
import NoticeDetailPage from './pages/notice/noticeDetailPage';
import NoticeUpdatePage from './pages/notice/noticeUpdatePage';
import NoticeCreatePage from './pages/notice/noticeCreatePage';
// 회원관리 페이지
import MemberPage from './pages/member/memberPage';
import MemberDetailPage from './pages/member/memberDetailPage';

function App() {
  return (
    <div className="App">
      <AxiosInterceptor>
        <Router>
          <Routes>
            <Route path="" element={<LoginPage />} />
            <Route path="/" element={<Navbar />}>
              <Route path="main" element={<MainPage />} />

              <Route path="notice/">
                <Route path="" element={<NoticePage />} />
                <Route path=":id" element={<NoticeDetailPage />} />
                <Route path="update/:id" element={<NoticeUpdatePage />} />
                <Route path="create" element={<NoticeCreatePage />} />
              </Route>

              <Route path="member/">
                <Route path="" element={<MemberPage />} />
                <Route path=":id" element={<MemberDetailPage />} />
              </Route>
            </Route>
          </Routes>
        </Router>
      </AxiosInterceptor>
    </div>
  );
}

export default App;
