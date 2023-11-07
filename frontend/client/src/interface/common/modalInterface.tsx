import { ReactNode } from 'react';

export interface ModalContentInterface {
  content: string;
  okAction: () => void;
}

export interface ModalSelectInterface {
  content: string;
  okAction: () => void;
  cancelAction: () => void;
}

export interface ModalPropsInterface {
  closeModal: () => void;
  OpenModal: boolean;
  children: ReactNode;
  width: string;
  height: string;
}

export interface ModalClassTypeInterface {
  modalOpen: { [key: string]: boolean };
  openModal: (modalName: string) => void;
  closeModal: (modalName: string) => void;
}

export interface ModalProviderPropsInterface {
  children: ReactNode;
}

export interface ErrorModalInterface {
  content: string | null;
}

export interface ConfirmModalInterface {
  content: string | null;
}

export interface UpdateConfirmInterface {
  content: string | null;
  id: string;
}
export interface UpdateModalInterface extends UpdateConfirmInterface {
  request: FormData;
}

export interface createModalInterface {
  content: string | null;
  request: FormData;
}

export interface DeleteModalInterface {
  content: string | null;
  id: string;
}

export interface ActiveModalInterface {
  content: string | null;
  id: string;
}
