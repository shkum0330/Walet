/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {},
    fontFamily: {
      'do-hyeon': ['Do Hyeon', 'Do Hyeon'],
    },
  },
  plugins: [require('tailwind-scrollbar-hide')],
};
