package com.ssafy.member.service.impl;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.global.config.ClientConfig;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.FeignClient;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.global.common.status.FailCode.*;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private FeignClient feignClient;
    @Autowired
    private ClientConfig clientConfig;

    // 회원가입
    @Override
    public MemberEntity signUp(String name, String email, String password, String phoneNumber, String birth, String pinNumber, String fingerPrint){
        if(memberRepository.existsByEmail(email)){
            throw new GlobalRuntimeException(EMAIL_EXIST);
        }
        String hashedPassword = PasswordEncoder.hashPassword(password);
        MemberEntity member = new MemberEntity();

        member.setName(name);
        member.setEmail(email);
        member.setPassword(hashedPassword);
        member.setPhoneNumber(phoneNumber);
        member.setBirth(birth);
        member.setRole(Role.USER);
        member.setPinNumber(pinNumber);
        member.setFingerPrint(fingerPrint);

        return memberRepository.save(member);
    }

    // 회원탈퇴
    @Override
    public void SignOut(Long id) {
        MemberEntity member = findMemberEntity(id);
        if (member == null) {
            throw new GlobalRuntimeException(FIND_IMPOSSIBLE);
        }

        member.setIsDeleted(true);
        memberRepository.save(member);
    }

    // id로 찾아 dto 반환
    @Override
    public MemberDto.UserResponse findById(Long id){
        MemberEntity member = findMemberEntity(id);

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
    public void verificationCode(String code, String savedCode){
        if(!code.equals(savedCode)){
            throw new GlobalRuntimeException(UNMATCHED_CODE);
        }
    }

    @Override
    public List<MemberDto.UsersResponse> getAllUsers(String accessToken) {
        String role = jwtProvider.RoleDecoder(accessToken);
        if(role.equals("USER")){
            throw new GlobalRuntimeException(STAFF_ONLY);
        }
        List<MemberEntity> members = memberRepository.findByRole(Role.USER);

        List<MemberDto.UsersResponse> userResponses = new ArrayList<>();
        for (MemberEntity member : members) {
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
    public MemberDto.NameResponse findNameById(Long id){

        MemberEntity member = findMemberEntity(id);
        MemberDto.NameResponse name = new MemberDto.NameResponse();
        name.setName(member.getName());
        return name;
    }

    @Override
    public MemberDto.PhoneResponse findPhoneById(Long id){

        MemberEntity member = findMemberEntity(id);
        MemberDto.PhoneResponse phone = new MemberDto.PhoneResponse();
        phone.setPhoneNumber(member.getPhoneNumber());
        return phone;
    }

    @Override
    public MemberEntity findMemberEntity(Long id){
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalRuntimeException(FIND_IMPOSSIBLE));
        return member;
    }

    @Override
    public void reviseMember(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalRuntimeException(FIND_IMPOSSIBLE));
        member.setIsDeleted(!member.isDeleted());
        memberRepository.save(member);
    }

    @Override
    public List<MemberDto.UsersResponse> searchUser(String keyword, String token) {
        List<MemberEntity> members = memberRepository.findByNameContaining(keyword);
        List<Long> memberIds = members.stream().map(MemberEntity::getId).collect(Collectors.toList());

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

        List<MemberEntity> users = memberRepository.findByCreatedDateAfter(startDate);
        MemberDto.CountResponse countData = new MemberDto.CountResponse();
        countData.setNewUser(String.valueOf(users.size()));
        countData.setAllUsers(memberRepository.count());
        countData.setGeneralAccountCount(transactionData.getData().getGeneralAccountCount());
        countData.setPetAccountCount(transactionData.getData().getPetAccountCount());
        return countData;
    }

}
