# Walet - 반려동물을 위한 pet 계좌 서비스

| <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1671380429/noticon/zczk9wzsj5jq0epa0ine.png" alt="Notion Image" width="20" height="20"> [Notion](https://ambitious-cafe-d87.notion.site/A301-14b333fbc0ba4faebe0fc6498f000959?pvs=4) | <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1640982247/noticon/tpvr26zp02angin4t0jv.png" alt="Notion Image" width="20" height="20"> [Figma](https://www.figma.com/file/301GJlg4OmMEoFQxXut2ZQ/SSAFY_%EA%B8%B0%EC%97%85%EC%97%B0%EA%B3%84_%EB%86%8D%ED%98%91?type=design&node-id=0-1&mode=design&t=ursBjYyjVusKYyaj-0) |

<br/>

## 기간 & 특이사항

- 2023.10.09 ~ 2023.11.17
- 농협은행 기업 연계 프로젝트

<br/>

## 서비스 소개

- 반려동물 전용 사용 용도 계좌를 활용한 내/외장 등록칩 기반 간편결제 서비스
- 농협의 API를 활용하여 이체 및 양도 기능 제공

## 기획 배경

- 반려 가구는 총 552만 가구로 반려동물과 함께하는 시대가 다가왔음
- 반려동물을 위한 금융 상품들의 대두 (펫 적금, 펫 보험 등)

## 프로젝트 간 기업 요구사항

### 필수 사항

- 회원 가입 및 로그인
- 계좌 등록 및 NH 오픈 플랫폼 API를 통한 핀어카운트 발급
- NH 오픈 플랫폼 API를 통한 입출금 계좌 간 이체
- 거래 기간별 거래 내역 조회 및 거래 로그 저장
- 공지사항 게시판(모달/레이어 팝업) 및 사용자별 권한 부여
- 관리자 페이지
  - 공지사항 게시판 관리
  - 앱 서비스 메인 화면에 팝업 노출할 공지사항 선택 기능
  - 고객 정보 조회 및 수정

### 추가 구현 기능

- 입출금에 대한 FCM(Firebase Cloud Messaging)으로 푸시 알림
- 관리자 페이지 내 일별 전체거래량, 전체거래금액 등 시각화(차트)
- 분리된 Web, Was 서버 구현

## 주요 기능

## Mobile

### 이체

- 상대방의 계좌번호를 입력하고 원하는 금액을 송금<br/><br/>

 <img src="./asset/transfer.gif" alt="transfer"/>

### 결제 요청

- 사업자가 결제금액을 입력
- 가게에 있는 블루투스 기기로 반려동물의 등록 코드를 인식
- 반려동물의 정보가 뜨고 구매자가 '확인'을 누르면 주인 휴대폰으로 결제 요청 <br/> <br/>

<img src="./asset/askpay.gif" alt="askpay"/>

### 결제 승인

- 반려동물 주인이 결제 요청을 수락하면 펫 계좌에서 돈이 자동으로 사업자의 계좌로 이체<br/> <br/>

<img src="./asset/payaccept.gif" alt="payaccept" width="336" height="720"/>

### 펫 계좌 양도 신청

- 양도할 대상의 정보와 메시지를 입력하여 펫 계좌 양도 요청 <br/> <br/>

<img src="./asset/pettrade.gif" alt="pettrade" />

### 펫 계좌 양도 받기

- 양도 요청을 확인하고, 이를 수락하여 펫 계좌 양도가 완료 <br/> <br/>

<img src="./asset/pettrader.gif" alt="pettrader"/>

### 펫 계좌 충전

- 연결돼 있는 충전 계좌로부터 펫 계좌에 필요한 금액을 충전 <br/><br/>

<img src="./asset/charge.gif" alt="charge"/>

## Web

### 관리자 페이지(로그인)

- 관리자 전용 로그인 (일반 사용자 로그인 불가) <br/> <br/>

 <img src="./asset/adminPage.gif" alt="login"/>

### 로그 시각화 및 유저 거래 정보 조회

- 로그를 시각화하여 전체 거래량, 전체 거래 금액 등 확인 가능
- 발생한 거래 내역들에 대한 로그 체크 가능
- 유저의 계좌에서 발생한 거래 로그 확인 가능
- 유저의 상태 변경 가능 <br/><br/>

<img src="./asset/adminPage2.gif" alt="log"/>

### 공지사항 관리 및 메인 공지사항 설정

- 공지사항 생성, 수정, 삭제 가능
- 모바일 화면 상단의 팝업 노출 여부 선택 가능 <br/> <br/>

<img src="./asset/adminPage3.gif" alt="notice"/>

## 기술 스택

<h3 align="center">Frontend</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Node.js-339933?&logo=nodedotjs&logoColor=white">
    <img src="https://img.shields.io/badge/React-61DAFB?&logo=react&logoColor=white">
    <img src="https://img.shields.io/badge/TypeScript-3178C6?&logo=typescript&logoColor=white">
    <img src="https://img.shields.io/badge/Redux-764ABC?&logo=redux&logoColor=white">
    <img src="https://img.shields.io/badge/axios-5A29E4?&logo=axios&logoColor=white">
    <img src="https://img.shields.io/badge/ReactRouter-CA4245?&logo=reactrouter&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/ESLint-4B32C3?&logo=eslint&logoColor=white">
    <img src="https://img.shields.io/badge/Prettier-F7B93E?&logo=prettier&logoColor=white">
    <img src="https://img.shields.io/badge/Chart.js-FF6384?&logo=chartdotjs&logoColor=white">
    
</p>

<h3 align="center">Backend</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Java-007396?&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Gradle-02303A?&logo=gradle&logoColor=white">
    <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?&logo=springsecurity&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-000000?&logo=jsonwebtokens&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Hibernate-59666C?&logo=hibernate&logoColor=white">
      <img src="https://img.shields.io/badge/mariaDB-003545?&logo=mariaDB&logoColor=white"> 
    <img src="https://img.shields.io/badge/Redis-DC382D?&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/rabbitmq-FF6600?logo=rabbitmq&logoColor=white">
    <img src="https://img.shields.io/badge/FCM-4285F4?logo=firebase&logoColor=white">
    <img src="https://img.shields.io/badge/FeignClient-00A0DF?logo=spring&logoColor=white">
</p>

<h3 align="center">Mobile</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Kotlin-7F52FF?&logo=Kotlin&logoColor=white">
    <img src="https://img.shields.io/badge/Android-3DDC84?&logo=Android&logoColor=white">
    <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?&logo=Android%20Studio&logoColor=white">
</p>

<h3 align="center">CI/CD</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Docker-2496ED?&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/Jenkins-D24939?&logo=jenkins&logoColor=white">
 <img src="https://img.shields.io/badge/ubuntu-E95420?&logo=ubuntu&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/amazon EC2-FF9900?&logo=amazon ec2&logoColor=white">
    <img src="https://img.shields.io/badge/amazon RDS-527FFF?&logo=amazonrds&logoColor=white">
    <img src="https://img.shields.io/badge/amazon S3-569A31?&logo=amazons3&logoColor=white">
</p>

<h3 align="center">Co-work tool</h3>
<p align="center">
    <img src="https://img.shields.io/badge/GitLab-FC6D26?&logo=GitLab&logoColor=white">
    <img src="https://img.shields.io/badge/Notion-000000?&logo=Notion&logoColor=white">
    <img src="https://img.shields.io/badge/Jira-0052CC?&logo=Jira Software&logoColor=white">
    <img src="https://img.shields.io/badge/Postman-FF6C37?&logo=Postman&logoColor=white">
    <img src="https://img.shields.io/badge/Mattermost-0058CC?&logo=Mattermost&logoColor=white">
    <img src="https://img.shields.io/badge/Figma-F24E1E?&logo=Figma&logoColor=white">
</p>

## Architecture

 <img src="./asset/architecture.png" alt="architecture"/>

### ERD

<img src="./asset/ERD.png" alt="ERD"/>

## NH 멘토님

<table>
  <tr>
    <td align="center" width="200" height="100">
        <img src="./asset/NH.png" alt="김현욱 멘토님" />
    </td>
    <td align="center" width="200" height="100">
        <img src="./asset/NH.png" alt="원종호 멘토님" />
    </td>
  </tr>
  <tr>
    <td align="center">
        <b>김현욱 멘토님</b>
    </td>
    <td align="center">
        <b>원종호 멘토님</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <span> NH 은행 멘토</span>
    </td>
    <td align="center">
      <span>NH 은행 멘토</span>
    </td>
  </tr>
</table>

## 역할분담

<table>
  <tr>
    <td align="center" width="200">
        <img src="./asset/1.png" alt="이승엽" />
    </td>
     <td align="center" width="200">
        <img src="./asset/2.png" alt="금세현" />
    </td>
    <td align="center" width="200">
        <img src="./asset/3.png" alt="김보석" />
    </td>
    <td align="center" width="200">
        <img src="./asset/4.png" alt="송희도" />
    </td>
    <td align="center" width="200">
        <img src="./asset/5.png" alt="신재혁" />
    </td>
    <td align="center" width="200">
        <img src="./asset/6.png" alt="유지나" />
    </td>
  </tr>
  <tr>
    <td align="center">
        <b>이승엽</b>
    </td>
    <td align="center">
        <b>금세현</b>
    </td>
    <td align="center">
        <b>김보석</b>
    </td>
    <td align="center">
        <b>송희도</b>
    </td>
    <td align="center">
        <b>신재혁</b>
    </td>
    <td align="center">
        <b>유지나</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <span>Backend / 팀장</span>
    </td>
    <td align="center">
      <span>Backend</span>
    </td>
    <td align="center">
      <span>Frontend/IoT <br/>Backend/Infra</span>
    </td>
    <td align="center">
      <span>Backend/Infra</span>
    </td>
    <td align="center">
      <span>Mobile</span>
    </td>
    <td align="center">
      <span>Mobile</span>
    </td>
  </tr>
</table>
