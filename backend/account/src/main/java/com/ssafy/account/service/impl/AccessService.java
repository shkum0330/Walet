package com.ssafy.account.service.impl;

import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.api.request.access.AccountNumberForAccess;
import com.ssafy.account.api.response.access.AccessResponse;
import com.ssafy.account.common.api.exception.DuplicatedException;
import com.ssafy.account.common.api.exception.NotFoundException;
import com.ssafy.account.db.entity.access.Access;
import com.ssafy.account.db.entity.account.PetAccount;
import com.ssafy.account.db.repository.AccessRepository;
import com.ssafy.account.db.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.account.common.api.status.FailCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessService {

    private final AccessRepository accessRepository;
    private final AccountRepository accountRepository;

    @Override
    public Access createAccessRequest(Long requesterId, String requesterName, AccessSaveRequest request) {
        // 입력된 동물이름과 계좌번호로
        // 등록된 동물계좌가 없다면 예외발생
        PetAccount petAccount = accountRepository.findByPetNameAndAccountNumber(request.getPetName(), request.getAccountNumber());
        if(petAccount == null) {
            throw new NotFoundException(INCORRECT_PET_ACCOUNT_INFO);
        }

        // 이미 접근요청이 있다면 예외발생
        Access previousAccessRequest = accessRepository.findByRequestMemberIdAndAccountNumber(requesterId, request.getAccountNumber());
        if(previousAccessRequest != null) {
            throw new DuplicatedException(ALREADY_EXISTED_ACCESS);
        }

        Access access = new Access(requesterId, requesterName, request);
        return accessRepository.save(access);
    }

    @Override
    public Boolean acceptAccessRequest(Long accessId) {
        Access access = accessRepository.findById(accessId).orElseThrow(() -> new NotFoundException(NOT_EXIST_ACCESS_REQUEST));
        access.confirm();
        
        // 수락이 됐으면
        // 접근요청을 한 사람에게
        // 푸시알림을 보내줌
        // cf) Access 엔티티에는 요청을 보낸 사람의 id와 name이 들어있음

        return access.getIsConfirmed();
    }

    @Override
    public Long rejectAccessRequest(Long accessId) {
        Access access = accessRepository.findById(accessId).orElseThrow(() -> new NotFoundException(NOT_EXIST_ACCESS_REQUEST));
        accessRepository.delete(access);
        return accessId;
    }

    @Override
    public List<AccessResponse> getUnacceptedAccessRequestsForAccountOwner(AccountNumberForAccess access) {
        
        // 아직 확인이 안된 요청목록만 가져옴
        List<Access> accesses = accessRepository.findByAccountNumberAndIsConfirmed(access.getAccountNumber(), 0);
        
        // 요청 내용이 없으면 예외 발생
        if(accesses.isEmpty()) {
            throw new NotFoundException(EMPTY_ACCESS_REQUEST);
        }

       return accesses.stream().map(AccessResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessResponse> getUnacceptedAccessRequestsForRequester(Long memberId) {

        // 아직 확인이 안된 요청목록만 가져옴
        List<Access> accesses = accessRepository.findByRequestMemberIdAndIsConfirmed(memberId, 0);
        
        // 요청 내용이 없으면 예외 발생
        if(accesses.isEmpty()) {
            throw new NotFoundException(EMPTY_ACCESS_REQUEST);
        }

        return accesses.stream().map(AccessResponse::new)
                .collect(Collectors.toList());
    }

}
