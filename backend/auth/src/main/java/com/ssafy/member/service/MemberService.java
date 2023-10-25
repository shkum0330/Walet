package com.ssafy.member.service;

import com.ssafy.member.db.MemberEntity;
import com.ssafy.member.db.MemberRepository;
import com.ssafy.member.db.Role;
import com.ssafy.member.error.EmailAlreadyExistsException;
import com.ssafy.member.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberEntity signup(String name, String email, String password, String phoneNumber, String birth){
        if(memberRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("존재하는 이메일입니다.");
        }

        MemberEntity member = new MemberEntity();
        member.setName(name);
        member.setEmail(email);
        String hashedPassword = PasswordEncoder.hashPassword(password);
        member.setPassword(hashedPassword);
        member.setPhoneNumber(phoneNumber);
        member.setBirth(birth);
        member.setRole(Role.USER);
        return memberRepository.save(member);
    }
}
