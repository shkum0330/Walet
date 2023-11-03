import { ErrorModalInterface } from '../../interface/common/modalInterface';
import ConfirmContents from './confirmContent';
import Modal from './modal';
import { useModal } from './modalClass';

function ErrorModal({ content }: ErrorModalInterface) {
  const { modalOpen, closeModal } = useModal();
  const errorModal = 'error';

  return (
    <div>
      <Modal
        closeModal={() => {
          closeModal(errorModal);
        }}
        OpenModal={modalOpen[errorModal]}
        width="w-[20%] bg-color-white"
        height="h-200px">
        <ConfirmContents
          content={content as string}
          okAction={() => {
            closeModal(errorModal);
          }}
        />
      </Modal>
    </div>
  );
}

export default ErrorModal;
