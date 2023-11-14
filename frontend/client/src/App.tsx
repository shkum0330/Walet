import {
  BrowserRouter as Router,
  Routes,
  Route,
  Outlet,
  Navigate,
} from 'react-router-dom';
import { AxiosInterceptor } from './repository/axios/instanceRepository';
import LoginPage from './pages/login/loginPage';
import MainPage from './pages/main/mainPage';
import Navbar from './components/nav/navbar';
// 공지사항 페이지
import NoticePage from './pages/notice/noticePage';
import NoticeDetailPage from './pages/notice/noticeDetailPage';
import NoticeUpdatePage from './pages/notice/noticeUpdatePage';
import NoticeCreatePage from './pages/notice/noticeCreatePage';
// 회원관리 페이지
import MemberPage from './pages/member/memberPage';
import LogPage from './pages/log/logPage';
import { useAccessToken } from './data_source/apiInfo';

function PrivateLoginRoute() {
  const token = useAccessToken();
  if (token) {
    return <Outlet />;
  }
  return <Navigate to="/" />;
}

function PrivateNotLoginRoute() {
  const token = useAccessToken();
  if (!token) {
    return <Outlet />;
  }
  return <Navigate to="/" />;
}

function App() {
  return (
    <div className="App">
      <AxiosInterceptor>
        <Router>
          <Routes>
            <Route element={<PrivateNotLoginRoute />}>
              <Route path="" element={<LoginPage />} />
            </Route>

            <Route element={<PrivateLoginRoute />}>
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
                </Route>

                <Route path="log/">
                  <Route path="" element={<LogPage />} />
                </Route>
              </Route>
            </Route>
          </Routes>
        </Router>
      </AxiosInterceptor>
    </div>
  );
}

export default App;
