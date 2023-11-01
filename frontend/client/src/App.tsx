import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AxiosInterceptor } from './repository/instanceRepository';
import LoginPage from './pages/loginPage';

function App() {
  return (
    <div className="App">
      <AxiosInterceptor>
        <Router>
          <Routes>
            <Route path="/" element={<LoginPage />} />
          </Routes>
        </Router>
      </AxiosInterceptor>
    </div>
  );
}

export default App;
