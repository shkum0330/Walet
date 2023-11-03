import { CardProps } from '../../interface/common/commonInterface';

function Card({ children, width, height }: CardProps) {
  return (
    <div className={`flex justify-center mt-8 mx-4 ${height} ${width}`}>
      <div className="rounded-xl overflow-hidden shadow-md w-full h-full p-2">
        {children}
      </div>
    </div>
  );
}

export default Card;
