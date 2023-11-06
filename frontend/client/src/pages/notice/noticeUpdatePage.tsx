import { useEffect, useState } from 'react';
import Card from '../../components/common/card';
import Basic from '../../assets/imgs/basic.png';

function NoticeUpdatePage() {
  const [title, setTitle] = useState<string>('');
  const [subTitle, setSubTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [previewUrl, setPreviewUrl] = useState<string | null>();
  const [ProfileImage, setProfileImage] = useState<File | null>(null);
  const [Url, setUrl] = useState<string>();

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      setProfileImage(file);

      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewUrl(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">공지사항 상세보기</p>
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
                    {previewUrl && (
                      <img
                        src={previewUrl}
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
            <div className="h-[80%]">
              <p className="text-2xl">작성 내용</p>
              <div className="flex">
                <label htmlFor="fileInput" className="w-[50%]">
                  {previewUrl ? (
                    <img
                      src={previewUrl}
                      alt="ImagePreview"
                      className="w-full h-full"
                    />
                  ) : (
                    <img
                      src={Basic}
                      alt="기본 이미지"
                      className="m-1 ring-1 w-full h-full"
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
                      className="h-[60%] w-full"
                    />
                  </div>
                </div>
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
              {previewUrl && (
                <img
                  src={previewUrl}
                  alt="banner"
                  className="w-full h-[30%] bg-[#FFEB7A]"
                />
              )}

              <p className="text-2xl text-center mt-8">{title}</p>
              <p className="text-lx text-center mt-1">{subTitle}</p>
              <div className="h-full">
                <p className="mt-3"> {content}</p>
              </div>
            </Card>
          </div>
        </Card>
      </div>
    </div>
  );
}

export default NoticeUpdatePage;
