import { UpdateConfirmInterface } from '../../interface/common/modalInterface';
import ConfirmContents from './confirmContent';
import Modal from './modal';
import { useModal } from './modalClass';

function ConfirmUpdate({ content, id }: UpdateConfirmInterface) {
  const { modalOpen, closeModal } = useModal();
  const updateModal = 'updateconfirm';

  return (
    <div>
      <Modal
        closeModal={() => {
          closeModal(updateModal);
        }}
        OpenModal={modalOpen[updateModal]}
        width="w-[20%] bg-color-white"
        height="h-200px">
        <ConfirmContents
          content={content as string}
          okAction={() => {
            closeModal(updateModal);
            window.location.href = `/notice/${id}`;
          }}
        />
      </Modal>
    </div>
  );
}

export default ConfirmUpdate;
