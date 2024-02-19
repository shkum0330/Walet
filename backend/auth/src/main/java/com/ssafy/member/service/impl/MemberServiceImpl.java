package com.ssafy.member.service.impl;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.global.config.ClientConfig;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.FeignClient;
import com.ssafy.member.db.Member;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.global.common.status.FailCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final FeignClient feignClient;
    private final ClientConfig clientConfig;

    // 회원가입
    @Override
    public Member signUp(MemberDto.MemberRequest memberRequest){
        if(memberRepository.existsByEmail(memberRequest.getEmail())){
            throw new GlobalRuntimeException(EMAIL_EXIST);
        }
        String hashedPassword = PasswordEncoder.hashPassword(memberRequest.getPassword());
        Member member=Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .password(memberRequest.getPassword())
                .phoneNumber(memberRequest.getPhoneNumber())
                .birth(memberRequest.getBirth())
                .role(Role.USER)
                .pinNumber(memberRequest.getPinNumber())
                .fingerPrint(memberRequest.getFingerPrint())
                .build();
        return memberRepository.save(member);
    }

    // 회원탈퇴
    @Override
    public void SignOut(Long id) {
        Member member = findMemberEntity(id);
        if (member == null) {
            throw new GlobalRuntimeException(FIND_IMPOSSIBLE);
        }

        member.setIsDeleted(true);
        memberRepository.save(member);
    }

    // id로 찾아 dto 반환
    @Override
    @Transactional(readOnly = true)
    public MemberDto.UserResponse findById(Long id){
        Member member = findMemberEntity(id);

        MemberDto.UserResponse userDto = new MemberDto.UserResponse();
        userDto.setId((member.getId()));
        userDto.setName(member.getName());
        userDto.setEmail(member.getEmail());
        userDto.setPhoneNumber(member.getPhoneNumber());
        userDto.setBirth(member.getBirth());
        userDto.setCreatedDate(member.getCreatedDate());
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkEmailExists(String email){
        boolean emailExists = memberRepository.existsByEmail(email);
        if(emailExists){
            throw new GlobalRuntimeException(EMAIL_EXIST);
        }
        return memberRepository.existsByEmail(email);
    }

    @Override
    public String createVerificationCode() {
        Random random = new Random();
        String verificationCode = "";
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            verificationCode += Integer.toString(randomNumber);
        }
        return verificationCode;
    }

    @Override
    @Transactional(readOnly = true)
    public void verificationCode(String code, String savedCode){
        if(!code.equals(savedCode)){
            throw new GlobalRuntimeException(UNMATCHED_CODE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDto.UsersResponse> getAllUsers(String accessToken) {
        String role = jwtProvider.RoleDecoder(accessToken);
        if(role.equals("USER")){
            throw new GlobalRuntimeException(STAFF_ONLY);
        }
        List<Member> members = memberRepository.findByRole(Role.USER);

        List<MemberDto.UsersResponse> userResponses = new ArrayList<>();
        for (Member member : members) {
            MemberDto.UsersResponse usersResponse = new MemberDto.UsersResponse();
            usersResponse.setId(member.getId());
            usersResponse.setName(member.getName());
            usersResponse.setEmail(member.getEmail());
            usersResponse.setPhoneNumber(member.getPhoneNumber());
            usersResponse.setCreatedDate(member.getCreatedDate());
        }

        return userResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.NameResponse findNameById(Long id){

        Member member = findMemberEntity(id);
        MemberDto.NameResponse name = new MemberDto.NameResponse();
        name.setName(member.getName());
        return name;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.PhoneResponse findPhoneById(Long id){

        Member member = findMemberEntity(id);
        MemberDto.PhoneResponse phone = new MemberDto.PhoneResponse();
        phone.setPhoneNumber(member.getPhoneNumber());
        return phone;
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberEntity(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalRuntimeException(FIND_IMPOSSIBLE));
        return member;
    }

    @Override
    public void reviseMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalRuntimeException(FIND_IMPOSSIBLE));
        member.setIsDeleted(!member.isDeleted());
        memberRepository.save(member);
    }

    @Override
    public List<MemberDto.UsersResponse> searchUser(String keyword, String token) {
        List<Member> members = memberRepository.findByNameContaining(keyword);
        List<Long> memberIds = members.stream().map(Member::getId).collect(Collectors.toList());

        MemberDto.transactionRequest memberIdsRequest = new MemberDto.transactionRequest();
        memberIdsRequest.setAllMemberIds(memberIds);
        MemberDto.accountResponse accountResponse = feignClient.getExternalData(memberIdsRequest, token);
        Map<Long, List<MemberDto.accountResponse.AccountData>> accountDataMap = new HashMap<>();
        for (MemberDto.accountResponse.AccountData accountData : accountResponse.getData()) {
            accountDataMap.computeIfAbsent(accountData.getMemberId(), k -> new ArrayList<>()).add(accountData);
        }

        return members.stream()
                .map(member -> {
                    MemberDto.UsersResponse usersResponse = new MemberDto.UsersResponse(member);
                    usersResponse.setAccount(accountDataMap.get(member.getId()));
                    usersResponse.setIsDeleted(member.isDeleted()); // 추가된 부분
                    return usersResponse;
                })
                .collect(Collectors.toList());
    }


    @Override
    public MemberDto.CountResponse countDashBoardData(int days, String accessToken) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        MemberDto.TransactionResponse transactionData = feignClient.getTransactionData(accessToken);

        List<Member> users = memberRepository.findByCreatedDateAfter(startDate);
        MemberDto.CountResponse countData = new MemberDto.CountResponse();
        countData.setNewUser(String.valueOf(users.size()));
        countData.setAllUsers(memberRepository.count());
        countData.setGeneralAccountCount(transactionData.getData().getGeneralAccountCount());
        countData.setPetAccountCount(transactionData.getData().getPetAccountCount());
        return countData;
    }

}
