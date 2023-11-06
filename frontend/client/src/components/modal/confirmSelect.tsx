import { ModalSelectInterface } from '../../interface/common/modalInterface';

function ConfirmSelect({
  content,
  okAction,
  cancelAction,
}: ModalSelectInterface) {
  return (
    <div className="h-full p-5 bg-white rounded-2xl shadow-md flex flex-col justify-center items-center">
      <p className="text-xl text-gray-500 my-2">{content}</p>
      <div className="flex justify-center mt-3 w-[90%] ">
        <button
          type="button"
          className="rounded-md w-full py-2 mx-2 hover:bg-green-500 bg-green-400"
          onClick={cancelAction}>
          취소
        </button>
        <button
          type="button"
          className="rounded-md w-full py-2 mx-2 hover:bg-green-500 bg-green-400"
          onClick={okAction}>
          확인
        </button>
      </div>
    </div>
  );
}

export default ConfirmSelect;
