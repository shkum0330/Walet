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
  aspectRatio: 2,

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
    },
    y: {
      grid: {
        display: false,
      },
    },
  },
};

function WeeklyTransactionGraph() {
  const labels = ['월', '화', '수', '목', '금', '토', '일'];

  const data = {
    labels,
    datasets: [
      {
        data: Array(7)
          .fill(0)
          .map(() => Math.ceil(Math.random() * 1000)),
        borderColor: '#FFB71B',
        backgroundColor: 'rgba(255, 183, 27, 0.2)',
        fill: true,
      },
    ],
  };

  return (
    <div>
      <p className="text-xl">기간 거래량 분석</p>
      <div className="flex items-center justify-center h-[25vh] w-[36vh] mt-4">
        <Line options={options} data={data} />
      </div>
    </div>
  );
}

export default WeeklyTransactionGraph;
