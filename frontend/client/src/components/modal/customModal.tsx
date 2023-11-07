import {
  ConfirmModalInterface,
  UpdateConfirmInterface,
  createModalInterface,
  ErrorModalInterface,
  UpdateModalInterface,
} from '../../interface/common/modalInterface';
import {
  noticeCreateRepository,
  noticeUpdateRepository,
} from '../../repository/notice/noticeRepository';
import { ConfirmContents, ConfirmSelect } from './modalContent';
import Modal from './modal';
import { useModal } from './modalClass';

export function ConfirmModal({ content }: ConfirmModalInterface) {
  const { modalOpen, closeModal } = useModal();
  const confirmModal = 'confirm';

  return (
    <div>
      <Modal
        closeModal={() => {
          closeModal(confirmModal);
        }}
        OpenModal={modalOpen[confirmModal]}
        width="w-[20%] bg-color-white"
        height="h-200px">
        <ConfirmContents
          content={content as string}
          okAction={() => {
            closeModal(confirmModal);
            window.location.href = '/notice';
          }}
        />
      </Modal>
    </div>
  );
}

export function ConfirmUpdate({ content, id }: UpdateConfirmInterface) {
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

export function CreateModal({ content, request }: createModalInterface) {
  const { openModal, modalOpen, closeModal } = useModal();
  const createModal = 'create';

  const responseHandle = async () => {
    const data = await noticeCreateRepository(request);
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

export function ErrorModal({ content }: ErrorModalInterface) {
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

export function UpdateModal({ content, id, request }: UpdateModalInterface) {
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
