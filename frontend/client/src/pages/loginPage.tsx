import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { LoginRepository } from '../repository/member/memberRepository';

function LoginPage() {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch();

  const handleLogin = (event: React.FormEvent) => {
    if (userId && password) {
      LoginRepository({ email: userId, password, dispatch });
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
              value={userId}
              onChange={event => setUserId(event.target.value)}
            />
          </div>
          <div className="mb-6">
            <input
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
              type="password"
              placeholder="password"
              value={password}
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
    </div>
  );
}

export default LoginPage;
