import { ConfirmModalInterface } from '../../interface/common/modalInterface';
import ConfirmContents from './confirmContent';
import Modal from './modal';
import { useModal } from './modalClass';

function ConfirmModal({ content }: ConfirmModalInterface) {
  const { modalOpen, closeModal } = useModal();
  const errorModal = 'confirm';

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
            window.location.href = '/notice';
          }}
        />
      </Modal>
    </div>
  );
}

export default ConfirmModal;
