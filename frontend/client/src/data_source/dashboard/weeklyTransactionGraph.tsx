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
    y: {
      max: 800,
      ticks: {
        stepSize: 200,
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
          .map(() => Math.ceil(Math.random() * 800)),
        borderColor: '#FFB71B',
        backgroundColor: '#FFB71B',
      },
    ],
  };

  return (
    <div>
      <p className="text-xl">기간 거래량 분석</p>
      <div className="h-[40px]" />
      <div className="flex items-center justify-center h-[400x] w-[95%] mt-4">
        <Line options={options} data={data} />
      </div>
    </div>
  );
}

export default WeeklyTransactionGraph;
