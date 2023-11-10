package com.ssafy.account.api.request.account;

import lombok.Data;

import java.util.List;

@Data
public class AdminAllMemberIdsRequest {
    List<Long> allMemberIds;
}
