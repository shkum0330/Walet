import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AxiosInterceptor } from './repository/instanceRepository';
import LoginPage from './pages/loginPage';
import MainPage from './pages/mainPage';
import Navbar from './components/nav/navbar';
import NoticePage from './pages/notice/noticePage';
import NoticeDetailPage from './pages/notice/noticeDetailPage';

function App() {
  return (
    <div className="App">
      <AxiosInterceptor>
        <Router>
          <Routes>
            <Route path="" element={<LoginPage />} />
            <Route path="/" element={<Navbar />}>
              <Route path="main" element={<MainPage />} />
              <Route path="notice" element={<NoticePage />} />
              <Route path="notice/:id" element={<NoticeDetailPage />} />
            </Route>
          </Routes>
        </Router>
      </AxiosInterceptor>
    </div>
  );
}

export default App;
