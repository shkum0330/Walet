package com.ssafy.member.service;

import com.ssafy.global.common.exception.GlobalRuntimeException;
import com.ssafy.member.api.MemberDto;
import com.ssafy.member.api.UserDto;
import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import com.ssafy.global.PasswordEncoder;
import com.ssafy.member.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberEntity signup(String name, String email, String password, String phoneNumber, String birth, String pinNumber, String fingerPrint){
        if(memberRepository.existsByEmail(email)){
            throw new GlobalRuntimeException("중복된 이메일 입니다.", HttpStatus.BAD_REQUEST);
        }

        MemberEntity member = new MemberEntity();
        member.setName(name);
        member.setEmail(email);
        String hashedPassword = PasswordEncoder.hashPassword(password);
        member.setPassword(hashedPassword);
        member.setPhoneNumber(phoneNumber);
        member.setBirth(birth);
        member.setRole(Role.USER);
        member.setPinNumber(pinNumber);
        member.setFingerPrint(fingerPrint);

        String randomMemberId = UuidUtil.generateRandomUUID();
        member.setRandomMemberId(randomMemberId);

        return memberRepository.save(member);
    }

    public void Signout(String randomMemberId) {
        MemberEntity member = memberRepository.findByRandomMemberId(randomMemberId);
        if (member == null) {
            throw new GlobalRuntimeException("해당 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        member.setIsDeleted(true);
        memberRepository.save(member);
    }

    public UserDto.Response find(String randomMemberId){
        MemberEntity member = memberRepository.findByRandomMemberId(randomMemberId);

        UserDto.Response userDto = new UserDto.Response();
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
            throw new GlobalRuntimeException("중복된 이메일 입니다.", HttpStatus.BAD_REQUEST);
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
            throw new GlobalRuntimeException("인증 번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
