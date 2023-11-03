import { ReactNode } from 'react';

export interface ModalContentInterface {
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
