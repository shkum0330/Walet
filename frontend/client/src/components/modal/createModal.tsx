import { createModalInterface } from '../../interface/common/modalInterface';
import { noticeCreateRepository } from '../../repository/notice/noticeRepository';
import ConfirmModal from './confirmModal';
import ConfirmSelect from './confirmSelect';
import Modal from './modal';
import { useModal } from './modalClass';

function CreateModal({ content, request }: createModalInterface) {
  const { openModal, modalOpen, closeModal } = useModal();
  const createModal = 'create';

  const responseHandle = async () => {
    const data = await noticeCreateRepository(request as FormData);
    if (data) {
      closeModal(createModal);
      openModal('confirm');
    }
  };

  return (
    <div>
      <Modal
        closeModal={() => {
          closeModal(createModal);
        }}
        OpenModal={modalOpen[createModal]}
        width="w-[25%] bg-color-white"
        height="h-200px">
        <ConfirmSelect
          content={content as string}
          cancelAction={() => {
            closeModal(createModal);
          }}
          okAction={() => {
            responseHandle();
          }}
        />
      </Modal>

      <ConfirmModal content="정상적으로 등록됐습니다." />
    </div>
  );
}

export default CreateModal;
