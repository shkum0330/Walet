import { CardProps } from '../../interface/common/commonInterface';

function Card({ children, width, height, styling }: CardProps) {
  return (
    <div className={`flex justify-center mt-3 mx-4 ${height} ${width}`}>
      <div
        className={`rounded-xl overflow-hidden shadow-md ${styling} w-full h-full `}>
        {children}
      </div>
    </div>
  );
}

export default Card;
