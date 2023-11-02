package com.ssafy.member.service;

import com.ssafy.auth.util.JwtProvider;
import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.global.common.status.FailCode;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import com.ssafy.global.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ssafy.global.common.status.FailCode.*;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;

    public MemberEntity signup(String name, String email, String password, String phoneNumber, String birth, String pinNumber, String fingerPrint){
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

    public void Signout(Long id) {
        MemberEntity member = findMember(id);
        if (member == null) {
            throw new GlobalRuntimeException(FIND_IMPOSSIBLE);
        }

        member.setIsDeleted(true);
        memberRepository.save(member);
    }

    public MemberDto.UserResponse find(Long id){
        MemberEntity member = findMember(id);

        MemberDto.UserResponse userDto = new MemberDto.UserResponse();
        userDto.setId((member.getId()));
        userDto.setName(member.getName());
        userDto.setEmail(member.getEmail());
        userDto.setPhoneNumber(member.getPhoneNumber());
        userDto.setBirth(member.getBirth());
        userDto.setCreatedDate(member.getCreated_date());
        return userDto;
    }

    public boolean checkEmailExists(String email){
        boolean emailExists = memberRepository.existsByEmail(email);
        if(emailExists){
            throw new GlobalRuntimeException(EMAIL_EXIST);
        }
        return memberRepository.existsByEmail(email);
    }

    public String createVerificationCode() {
        Random random = new Random();
        String verificationCode = "";
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            verificationCode += Integer.toString(randomNumber);
        }
        return verificationCode;
    }
    public void verificationCode(String code, String savedCode){
        if(!code.equals(savedCode)){
            throw new GlobalRuntimeException(UNMATCHED_CODE);
        }
    }

    public List<MemberDto.UsersResponse> getAllUsers(String accessToken) {
        String role = jwtProvider.RoleDecoder(accessToken);
        if(role.equals("USER")){
            throw new GlobalRuntimeException(STAFF_ONLY);
        }

        List<MemberEntity> members = memberRepository.findByRole(Role.USER);

        List<MemberDto.UsersResponse> userResponses = new ArrayList<>();
        for (MemberEntity member : members) {
            MemberDto.UsersResponse usersResponse = new MemberDto.UsersResponse(
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    member.getCreated_date()
            );
            userResponses.add(usersResponse);
        }

        return userResponses;
    }

    public String findNameById(Long id){
        MemberEntity member = findMember(id);
        return member.getName();
    }

    public MemberEntity findMember(Long id){
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalRuntimeException(FIND_IMPOSSIBLE));
        return member;
    }

}
