import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { useState } from 'react';
import { Line } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);
const options = {
  responsive: true,
  aspectRatio: 4,
  plugins: {
    legend: {
      display: false,
    },
  },
  elements: {
    line: {
      tension: 0.4,
    },
  },
};

function TransactionGraph() {
  const [period, setPeriod] = useState('1day');

  const handleClick = (newPeriod: string) => {
    setPeriod(newPeriod);
  };

  const getLabels = (selectedPeriod: string) => {
    switch (selectedPeriod) {
      case '1day':
        return Array(30)
          .fill(0)
          .map((_, index) => `${index + 1}일`);
      case '7days':
        return Array(12)
          .fill(0)
          .map((_, index) => `${index + 1}월`);
      case '1month':
        return ['2020년', '2021년', '2022년', '2023년'];
      default:
        return [];
    }
  };

  const getData = (selectedPeriod: string) => {
    switch (selectedPeriod) {
      case '1day':
        return Array(30)
          .fill(0)
          .map(() => Math.ceil(Math.random() * 1000000));
      case '7days':
        return Array(12)
          .fill(0)
          .map(() => Math.ceil(Math.random() * 7000000));
      case '1month':
        return Array(4)
          .fill(0)
          .map(() => Math.ceil(Math.random() * 30000000));
      default:
        return [];
    }
  };

  const data = {
    labels: getLabels(period),
    datasets: [
      {
        data: getData(period),
        borderColor: '#00C805',
        backgroundColor: '#00C805',
      },
    ],
  };

  return (
    <div>
      <p className="text-xl">기간별 거래금액 그래프</p>
      <div className="flex justify-end">
        <button
          type="button"
          className={`px-4 py-2 border ${
            period === '1day' ? 'bg-gray-200' : ''
          }`}
          onClick={() => handleClick('1day')}>
          1일
        </button>
        <button
          type="button"
          className={`px-4 py-2 border ${
            period === '7days' ? 'bg-gray-200' : ''
          }`}
          onClick={() => handleClick('7days')}>
          7일
        </button>
        <button
          type="button"
          className={`px-4 py-2 border ${
            period === '1month' ? 'bg-gray-200' : ''
          }`}
          onClick={() => handleClick('1month')}>
          1달
        </button>
      </div>
      <div className="flex items-center justify-center h-[200px] w-[95%] mt-4">
        <Line options={options} data={data} />
      </div>
    </div>
  );
}

export default TransactionGraph;
