package com.ssafy.account.api.response.payment;

import lombok.Builder;
import lombok.Data;

@Data
public class CheckResponse {
    private String petImage;
    private String petName;
    private String petBreed;
    private String petGender;
    private String petNeutered;
    private String petAge;
    private String petBirth;

    @Builder
    public CheckResponse(String petImage, String petName, String petBreed, String petGender, String petNeutered, String petAge, String petBirth) {
        this.petImage= petImage;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petNeutered = petNeutered;
        this.petAge = petAge;
        this.petBirth = petBirth;
    }
}
