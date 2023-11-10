package com.ssafy.member.db;

import com.ssafy.member.api.MemberDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.cloud.openfeign.FeignClient(name = "account", url="http://43.201.195.182")
public interface FeignClient {
    @GetMapping("api/account/admin/list/all-account/{memberId}")
    MemberDto.accountResponse getExternalData(@PathVariable("memberId") Long memberId);
}
