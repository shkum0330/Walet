package com.ssafy.account.api.response.account.transfer;

import lombok.Builder;
import lombok.Data;

@Data
public class NewOwnerResponse {
    String name;
    String date;
    String content;

    @Builder
    public NewOwnerResponse(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

}
