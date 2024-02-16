package com.ssafy.member.service;

import com.ssafy.member.api.MemberDto;
import com.ssafy.member.db.MemberEntity;

import java.util.List;

public interface MemberService {
    MemberEntity signUp(String name, String email, String password, String phoneNumber, String birth, String pinNumber, String fingerPrint);
    void SignOut(Long id);
    MemberDto.UserResponse findById(Long id);
    boolean checkEmailExists(String email);
    String createVerificationCode();
    void verificationCode(String code, String savedCode);
    List<MemberDto.UsersResponse> getAllUsers(String accessToken);
    MemberDto.NameResponse findNameById(Long id);
    MemberDto.PhoneResponse findPhoneById(Long id);
    MemberEntity findMemberEntity(Long id);
    void reviseMember(Long id);
    List<MemberDto.UsersResponse> searchUser(String keyword, String token);
    MemberDto.CountResponse countDashBoardData(int days, String accessToken);

}
