import { UpdateModalInterface } from '../../interface/common/modalInterface';
import { noticeUpdateRepository } from '../../repository/notice/noticeRepository';
import ConfirmSelect from './confirmSelect';
import ConfirmUpdate from './confirmUpdate';
import Modal from './modal';
import { useModal } from './modalClass';

function UpdateModal({ content, id, request }: UpdateModalInterface) {
  const { openModal, modalOpen, closeModal } = useModal();
  const updateModal = 'update';

  const responseHandle = async () => {
    const data = await noticeUpdateRepository(request, id);
    if (data) {
      closeModal(updateModal);
      openModal('updateconfirm');
    }
  };

  return (
    <div>
      <Modal
        closeModal={() => {
          closeModal(updateModal);
        }}
        OpenModal={modalOpen[updateModal]}
        width="w-[25%] bg-color-white"
        height="h-200px">
        <ConfirmSelect
          content={content as string}
          cancelAction={() => {
            closeModal(updateModal);
          }}
          okAction={() => {
            responseHandle();
          }}
        />
      </Modal>

      <ConfirmUpdate content="정상적으로 수정됐습니다." id={id} />
    </div>
  );
}

export default UpdateModal;
