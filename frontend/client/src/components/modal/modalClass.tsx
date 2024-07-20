import { createContext, useContext, useState, useMemo } from 'react';
import {
  ModalClassTypeInterface,
  ModalProviderPropsInterface,
} from '../../interface/common/modalInterface';

const ModalContext = createContext<ModalClassTypeInterface>({
  modalOpen: {},
  openModal: () => {},
  closeModal: () => {},
});

export function ModalProvider({ children }: ModalProviderPropsInterface) {
  const [modalOpen, setModalOpen] = useState<{ [key: string]: boolean }>({});

  const openModal = (modalName: string) => {
    setModalOpen(prevState => ({ ...prevState, [modalName]: true }));
  };

  const closeModal = (modalName: string) => {
    setModalOpen(prevState => ({ ...prevState, [modalName]: false }));
  };

  const contextValue = useMemo(
    () => ({ modalOpen, openModal, closeModal }),
    [modalOpen],
  );

  return (
    <ModalContext.Provider value={contextValue}>
      {children}
    </ModalContext.Provider>
  );
}

export const useModal = (): ModalClassTypeInterface => {
  const context = useContext(ModalContext);

  if (!context) {
    throw new Error('useModal must be used within a ModalProvider');
  }

  return context;
};
