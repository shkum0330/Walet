import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { LoginRepository } from '../repository/member/memberRepository';
import ErrorModal from '../components/modal/ErrorModal';
import { useModal } from '../components/modal/modalClass';

function LoginPage() {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>('');
  const { openModal } = useModal();
  const dispatch = useDispatch();

  const handleLogin = (event: React.FormEvent) => {
    if (userId && password) {
      (async () => {
        const data = await LoginRepository({
          email: userId,
          password,
          dispatch,
        });
        if (data) {
          setError(data);
          openModal('error');
        }
      })();
    }
    event.preventDefault();
  };

  return (
    <div className="Login flex items-center justify-center h-screen">
      <div className="w-full max-w-xs">
        <form
          className="bg-white text-center shadow-md rounded px-8 pt-6 pb-8 mb-4"
          onSubmit={handleLogin}>
          <p className="text-2xl m-4">Admin Login</p>
          <div className="mb-4">
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              type="text"
              placeholder="id"
              onChange={event => setUserId(event.target.value)}
            />
          </div>
          <div className="mb-6">
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
              style={{ fontFamily: 'Arial, sans-serif' }}
              type="password"
              placeholder="password"
              onChange={event => setPassword(event.target.value)}
            />
          </div>
          <div className="flex items-center justify-between">
            <button
              className="bg-green-400 hover:bg-green-500 text-white font-bold w-full py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="submit">
              로그인
            </button>
          </div>
        </form>
      </div>
      <ErrorModal content={error} />
    </div>
  );
}

export default LoginPage;
