import { CardProps } from '../../interface/common/commonInterface';

function Card({ children, width, height, styling }: CardProps) {
  return (
    <div className={`flex justify-center mt-8 mx-4 ${height} ${width}`}>
      <div
        className={`rounded-xl overflow-hidden shadow-md ${
          styling as string
        } w-full h-full `}>
        {children}
      </div>
    </div>
  );
}

export default Card;
