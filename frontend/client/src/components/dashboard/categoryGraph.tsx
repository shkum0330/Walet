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
import { Doughnut } from 'react-chartjs-2';
import { DashBoardCategoryRepository } from '../../repository/dashboard/dashboardRepository';

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
  plugins: {
    legend: {
      position: 'bottom' as const,
    },
  },
};

function CategoryGraph() {
  const [Category, setCategory] = useState<[]>([]);

  useEffect(() => {
    (async () => {
      const data = await DashBoardCategoryRepository();
      if (data) {
        setCategory(data.category);
      }
    })();
  }, []);

  const data = {
    labels: ['동물병원', '동물용품', '동물이용', '애견카페', '동물놀이터'],
    datasets: [
      {
        data: Category,
        borderColor: '#a7a7a7',
        backgroundColor: [
          '#00C805',
          '#6C79EF',
          '#FF8855',
          '#FFB71B',
          '#FC84FE',
        ],
        fill: true,
      },
    ],
  };

  return (
    <div>
      <p className="text-xl">카테고리별 매출</p>
      <div className="flex ml-8 items-center justify-center w-[70%] ">
        <Doughnut options={options} data={data} />
      </div>
    </div>
  );
}

export default CategoryGraph;
