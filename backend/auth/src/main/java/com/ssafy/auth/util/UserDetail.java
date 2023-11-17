package com.ssafy.auth.util;

import com.ssafy.member.db.MemberEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class UserDetail {

    private MemberEntity member;
    private Map<String, Object> attributes;

    @Builder
    public UserDetail(MemberEntity member){
        this.member = member;
    }

}
