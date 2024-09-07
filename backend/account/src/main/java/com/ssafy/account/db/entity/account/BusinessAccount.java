package com.ssafy.account.db.entity.account;

import com.ssafy.account.api.request.account.AccountSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("Business")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessAccount extends Account {

    @Column(name="business_type")
    private Integer businessType; // 사업유형 입력
    @Builder
    public BusinessAccount(Long memberId, String depositorName, AccountSaveRequest accountSaveRequest) {
        super(memberId,depositorName,accountSaveRequest);
        this.businessType = accountSaveRequest.getBusinessType();
    }
}
