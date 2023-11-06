import { Fragment } from 'react';
import { Menu, Transition } from '@headlessui/react';

function NoticeDropdown({ pageid }: { pageid: string }) {
  const handleUpdate = (id: string) => {
    window.location.href = `/notice/update/${id}`;
  };

  return (
    <Menu as="div" className="relative w-8 ml-2 inline-block text-left">
      <div>
        <Menu.Button className="inline-flex w-full rounded-lg justify-center gap-x-1.5 hover:rounded-t-md bg-gray-300 px-3 py-2 text-sm font-semibold text-gray-900 shadow-lg hover:ring-1 hover:ring-inset hover:ring-green-300 hover:bg-green-300">
          +
        </Menu.Button>
      </div>

      <Transition
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95">
        <Menu.Items className="absolute rounded-lg left-0 right-0 z-10  w-28 origin-top-right rounded-b-md bg-BACKGROUND-50 shadow-lg">
          <div>
            <Menu.Item>
              <button
                type="button"
                className="w-full bg-gray-100 text-gray-900 block px-4 py-2 text-sm hover:bg-green-100"
                onClick={() => handleUpdate(pageid)}>
                수정하기
              </button>
            </Menu.Item>
            <Menu.Item>
              <button
                type="button"
                className="w-full bg-gray-100 text-gray-900 block px-4 py-2 text-sm hover:bg-green-100">
                삭제하기
              </button>
            </Menu.Item>
          </div>
        </Menu.Items>
      </Transition>
    </Menu>
  );
}

export default NoticeDropdown;
