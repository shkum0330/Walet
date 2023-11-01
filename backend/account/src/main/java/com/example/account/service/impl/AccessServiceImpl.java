package com.example.account.service.impl;

import com.example.account.api.request.access.AccessSaveRequest;
import com.example.account.api.request.access.AccountNumberForAccess;
import com.example.account.api.response.access.AccessResponse;
import com.example.account.common.api.exception.NotFoundException;
import com.example.account.common.api.status.FailCode;
import com.example.account.db.entity.access.Access;
import com.example.account.db.repository.AccessRepository;
import com.example.account.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final AccessRepository accessRepository;

    @Override
    public Long createAccessRequest(Long requesterId, String requesterName, AccessSaveRequest request) {
        Access access = new Access(requesterId, requesterName, request);
        accessRepository.save(access);
        return access.getId();
    }

    @Override
    public int acceptAccessRequest(Long accessId) {
        Access access = accessRepository.findById(accessId).orElseThrow(() -> new NotFoundException(FailCode.NOT_EXIST_ACCESS_REQUEST));
        access.confirm();
        
        // 수락이 됐으면
        // 접근요청을 한 사람에게
        // 푸시알림을 보내줌
        // cf) Access 엔티티에는 요청을 보낸 사람의 id와 name이 들어있음

        return access.getIsConfirmed();
    }

    @Override
    public Long rejectAccessRequest(Long accessId) {
        accessRepository.deleteById(accessId);
        return accessId;
    }

    @Override
    public List<AccessResponse> getUnacceptedAccessRequestsForAccountOwner(AccountNumberForAccess access) {
        
        // 아직 확인이 안된 요청목록만 가져옴
        List<Access> accesses = accessRepository.findAccessesByAccountNumberAndIsConfirmed(access.getAccountNumber(), 0);
        
        // 요청 내용이 없으면 예외 발생
        if(accesses == null || accesses.isEmpty()) {
            throw new NotFoundException(FailCode.EMPTY_ACCESS_REQUEST);
        }

       return accesses.stream().map(acs ->
                        new AccessResponse(acs))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessResponse> getUnacceptedAccessRequestsForRequester(Long memberId) {

        // 아직 확인이 안된 요청목록만 가져옴
        List<Access> accesses = accessRepository.findAccessesByRequestMemberIdAndIsConfirmed(memberId, 0);
        
        // 요청 내용이 없으면 예외 발생
        if(accesses == null || accesses.isEmpty()) {
            throw new NotFoundException(FailCode.EMPTY_ACCESS_REQUEST);
        }

        return accesses.stream().map(acs ->
                        new AccessResponse(acs))
                .collect(Collectors.toList());
    }

}
