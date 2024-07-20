package com.ssafy.account.db.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyType {
    // 반려동물 관련 업종
    // 동물병원, 반려동물용품, 반려동물미용, 애견카페, 반려견놀이터, 반려동물장례
    // 카카오맵 api의 category_name 컬럼을
    // StringTokenizer로 쪼개서
    // 마지막 부분을 가져오면 될듯
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_type_id")
    private Long id;
    @Column(name = "company_type",length = 10,nullable = false)
    private String companyType;
}
