import Card from '../../components/common/card';

function MemberDetailPage() {
  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <div className="flex">
        <p className="text-3xl">회원 상세 정보</p>
      </div>

      <Card width="w-[95%]" height="h-[30%]" styling=" ">
        <div className="flex items-center">
          <p className="text-2xl">회원 정보</p>
        </div>
      </Card>

      <Card width="w-[95%]" height="h-[50%]" styling="">
        <p className="text-2xl">배너 미리보기</p>
      </Card>
    </div>
  );
}

export default MemberDetailPage;
