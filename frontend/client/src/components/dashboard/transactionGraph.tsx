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
import { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { DashBoardStatsRepository } from '../../repository/dashboard/dashboardRepository';
import { DashboardStats } from '../../interface/api/dashboardApiInterface';

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
  aspectRatio: 3,
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
  scales: {
    x: {
      grid: {
        display: false,
      },
      ticks: {
        display: false,
      },
    },
    y: {
      grid: {
        display: false,
      },
    },
  },
};

function TransactionGraph() {
  const [period, setPeriod] = useState('1day');
  const [stats, setStates] = useState<DashboardStats>();
  const handleClick = (newPeriod: string) => {
    setPeriod(newPeriod);
  };

  useEffect(() => {
    (async () => {
      const data = await DashBoardStatsRepository();
      if (data) {
        setStates(data);
      }
    })();
  }, []);

  const getLabels = (selectedPeriod: string) => {
    switch (selectedPeriod) {
      case '1day':
        return Array(30)
          .fill(0)
          .map((_, index) => `${index + 1}`);
      case '7days':
        return Array(15)
          .fill(0)
          .map((_, index) => `${index + 1}`);
      case '1month':
        return Array(12)
          .fill(0)
          .map((_, index) => `${index + 1}`);
      default:
        return [];
    }
  };

  const getData = (selectedPeriod: string) => {
    switch (selectedPeriod) {
      case '1day':
        return stats?.day.reverse();
      case '7days':
        return stats?.week.reverse();
      case '1month':
        return stats?.month.reverse();
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
        backgroundColor: 'rgba(0, 200, 5, 0.3)',
        fill: true,
      },
    ],
  };

  return (
    <div>
      <div className="flex mb-8">
        <p className="text-xl w-[77%]">기간별 거래금액 그래프</p>
        <div className="flex justify-end">
          <button
            type="button"
            className={`px-2 py-1 border ${
              period === '1day' ? 'bg-gray-200' : ''
            }`}
            onClick={() => handleClick('1day')}>
            <p>일</p>
          </button>
          <button
            type="button"
            className={`px-2 py-1 border ${
              period === '7days' ? 'bg-gray-200' : ''
            }`}
            onClick={() => handleClick('7days')}>
            <p>주</p>
          </button>
          <button
            type="button"
            className={`px-2 py-1 border ${
              period === '1month' ? 'bg-gray-200' : ''
            }`}
            onClick={() => handleClick('1month')}>
            <p>월</p>
          </button>
        </div>
      </div>
      <div className="flex items-center justify-center  w-[65vh] mt-4">
        <Line options={options} data={data} />
      </div>
    </div>
  );
}

export default TransactionGraph;
