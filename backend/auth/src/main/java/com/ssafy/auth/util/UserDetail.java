package com.ssafy.auth.util;

import com.ssafy.member.db.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class UserDetail {

    private Member member;
    private Map<String, Object> attributes;

    @Builder
    public UserDetail(Member member){
        this.member = member;
    }

}
