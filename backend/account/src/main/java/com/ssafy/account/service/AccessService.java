package com.ssafy.account.service;

import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.api.request.access.AccountNumberForAccess;
import com.ssafy.account.api.response.access.AccessResponse;
import com.ssafy.account.db.entity.access.Access;

import java.util.List;

public interface AccessService {

    // 요청 생성
    public Access createAccessRequest(Long requesterId, String requesterName, AccessSaveRequest request);

    // 요청 수락
    // Access 엔티티의 isConfirmed를 true로 바꾸고
    // 요청을 보낸 사람한테는 Firebase로 푸시알림을 보냄
    public Boolean acceptAccessRequest(Long accessId);

    // 요청 거절
    public Long rejectAccessRequest(Long accessId);

    // 요청 받은 계좌 주인한테 아직 수락되지 않은 접근요청 목록 반환
    List<AccessResponse> getUnacceptedAccessRequestsForAccountOwner(AccountNumberForAccess access);

    // 요청을 보낸 사람한테 아직 아직 수락되지 않은 접근요청 목록 반환
    List<AccessResponse> getUnacceptedAccessRequestsForRequester(Long memberId);
}
