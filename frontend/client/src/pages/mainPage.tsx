import Card from '../components/common/card';

function MainPage() {
  return (
    <div className="ml-24 pl-4 pt-4 h-[89vh]">
      <p className="text-3xl font-do-hyeon">DashBoard</p>
      <div className="flex h-[12%]">
        <Card width="w-[22%]" height="h-full">
          보유재산 안넣음
        </Card>
        <Card width="w-[22%]" height="h-full">
          신규 계좌 발급
        </Card>
        <Card width="w-[22%]" height="h-full">
          신규 고객 영업
        </Card>
        <Card width="w-[22%]" height="h-full">
          총 거래량
        </Card>
      </div>
      <div className="flex h-[38%]">
        <Card width="w-[22%]" height="h-full">
          시스템 개요
        </Card>
        <Card width="w-[47%]" height="h-full">
          기간 거래금액 그래프
        </Card>
        <Card width="w-[22%]" height="h-full">
          기간 거래량 분석
        </Card>
      </div>
      <div className="flex h-[28%]">
        <Card width="w-[22%]" height="h-full">
          카테고리별 매출
        </Card>
        <Card width="w-[47%]" height="h-full">
          최근 거래 내역
        </Card>
        <Card width="w-[22%]" height="h-full">
          공지사항 관리
        </Card>
      </div>
    </div>
  );
}

export default MainPage;
