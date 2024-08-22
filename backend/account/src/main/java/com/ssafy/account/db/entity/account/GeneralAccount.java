package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import com.ssafy.account.common.domain.util.PasswordEncoder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("General")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeneralAccount extends Account{

    @Builder
    public GeneralAccount(Long memberId, String depositorName, AccountSaveRequest accountSaveRequest) {
        super(memberId,depositorName,accountSaveRequest);
    }
}
