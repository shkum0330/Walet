package com.ssafy.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class InquireTransactionHistoryDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        String FinAcno;
        String Insymd;
        String Ineymd;
        String TrnsDsnc;
        String Lnsq;
        String PageNo;
        String Dmcnt;
    }

    @Setter
    @Getter
    public static class Response{
        String AthrCnfrTckt;
        String WebUrl;
        String AndInltUrl;
        String AndAppUrl;
        String AndWebUrl;
        String IosInltUrl;
        String IosAppUrl;
        String IosWebUrl;
        String CtntDataYn;
        String TotCnt;
        List<REC> REC;

        @Getter
        @Setter
        public static class REC{
            String Trdd;
            String Txtm;
            String MnrcDrotDsnc;
            String Tram;
            String AftrBlnc;
            String TrnsAfAcntBlncSmblCd;
            String Smr;
            String HnisCd;
            String HnbrCd;
            String Ccyn;
            String Tuno;
            String BnprCntn;
        }
    }
}
