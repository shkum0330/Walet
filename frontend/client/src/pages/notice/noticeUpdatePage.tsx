import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Card from '../../components/common/card';
import Basic from '../../assets/imgs/basic.png';
import { useModal } from '../../components/modal/modalClass';
import { noticeDetailRepository } from '../../repository/notice/noticeRepository';
import UpdateModal from '../../components/modal/updateModal';

function NoticeUpdatePage() {
  const { id } = useParams();
  const [title, setTitle] = useState<string>('');
  const [subTitle, setSubTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [previewImg, setPrevieImg] = useState<string | null>();
  const [bannerImg, setBannerImg] = useState<File | null>(null);
  const [request, setRequest] = useState<FormData>(new FormData());
  const { openModal } = useModal();

  useEffect(() => {
    if (id) {
      (async () => {
        const data = await noticeDetailRepository(id);
        if (data) {
          setTitle(data.title);
          setSubTitle(data.title);
          setContent(data.content);
          setPrevieImg(data.bannerImg);
        }
      })();
    }
  }, []);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      setBannerImg(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setPrevieImg(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleBack = () => {
    window.location.href = `/notice/${id as string}`;
  };

  const handleUpdate = () => {
    const data = {
      title,
      subTitle,
      content,
    };
    const dataJson = JSON.stringify(data);
    const reqeustData = new FormData();
    reqeustData.append(
      'data',
      new Blob([dataJson], { type: 'application/json' }),
    );
    if (previewImg && bannerImg) {
      reqeustData.append('bannerImg', bannerImg);
    }
    reqeustData.append('bannerImg', '');
    setRequest(reqeustData);
    openModal('update');
  };

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">공지사항 수정하기</p>
        <button
          type="button"
          className="ml-4 p-2 bg-gray-300 rounded-lg"
          onClick={handleBack}>
          돌아가기
        </button>
      </div>

      <div className="flex w-full h-full">
        <div className="w-[50%]">
          <Card width="w-[95%]" height="h-[30%]" styling="p-2 flex flex-col ">
            <div className="flex items-center">
              <p className="text-2xl">배너 미리보기</p>
            </div>

            <div className="w-full h-full flex justify-center">
              <Card width="w-[70%]" height="h-[50%]" styling="p-2 bg-gray-50">
                <div>
                  <div className="flex justify-around flex-nowrap w-full">
                    <div>
                      <div className="text-2xl mt-2">{title}</div>
                      <div className="text-xl mt-1">{subTitle}</div>
                    </div>
                    {previewImg && (
                      <img
                        src={previewImg}
                        alt="banner"
                        className="w-[40%] h-[8vh]"
                      />
                    )}
                  </div>
                </div>
              </Card>
            </div>
          </Card>

          <Card width="w-[95%]" height="h-[50%]" styling="">
            <p className="text-2xl ml-2 mt-2">작성 내용</p>
            <div className="h-[80%] ml-4 mt-4">
              <div className="flex h-[80%]">
                <label htmlFor="fileInput" className="w-[50%]">
                  {previewImg ? (
                    <img
                      src={previewImg}
                      alt="ImagePreview"
                      className="w-full h-full"
                    />
                  ) : (
                    <img
                      src={Basic}
                      alt="기본 이미지"
                      className="m-1 ring-1 w-[90%] h-[90%]"
                    />
                  )}
                </label>
                <input
                  type="file"
                  id="fileInput"
                  multiple
                  onChange={handleFileChange}
                  className="hidden"
                />
                <div className="ml-4 w-full">
                  <div>
                    <p>제목</p>
                    <input
                      type="text"
                      placeholder="제목을 입력하세요"
                      value={title}
                      onChange={e => setTitle(e.target.value)}
                      className="w-[90%]"
                    />
                  </div>
                  <div>
                    <p>부제목</p>
                    <input
                      type="text"
                      placeholder="부제목을 입력하세요"
                      value={subTitle}
                      onChange={e => setSubTitle(e.target.value)}
                      className="w-[90%]"
                    />
                  </div>
                  <div className="h-full">
                    <p>내용</p>
                    <textarea
                      placeholder="내용을 입력하세요"
                      value={content}
                      onChange={e => setContent(e.target.value)}
                      className="h-[50%] w-full"
                    />
                  </div>
                </div>
              </div>
              <div className="flex justify-end">
                <button
                  type="button"
                  className="ml-4 p-2 bg-gray-300 rounded-lg mr-8"
                  onClick={handleUpdate}>
                  수정하기
                </button>
              </div>
            </div>
          </Card>
        </div>

        <Card width="w-[40%]" height="h-[84%]" styling="">
          <p className="text-2xl ml-2 mt-2">상세 화면 미리보기</p>
          <div className="w-full h-full flex justify-center">
            <Card
              width="w-[360px]"
              height="h-[800px]"
              styling=" bg-gray-50 overflow-y-auto scrollbar-hide">
              {previewImg && (
                <img
                  src={previewImg}
                  alt="banner"
                  className="w-full h-[30%] bg-[#FFEB7A]"
                />
              )}

              <p className="text-2xl text-center mt-8">{title}</p>
              <p className="text-lx text-center mt-1">{subTitle}</p>
              <div className="h-full">
                <div
                  className="mt-3"
                  dangerouslySetInnerHTML={{
                    __html: content.replace(/\n/g, '<br/>'),
                  }}
                />
              </div>
            </Card>
          </div>
        </Card>
      </div>
      <UpdateModal
        content="공지사항을 수정하겠습니까?"
        request={request}
        id={id as string}
      />
    </div>
  );
}

export default NoticeUpdatePage;
