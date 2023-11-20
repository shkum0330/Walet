package com.allforyou.app

import android.os.Parcel
import android.os.Parcelable

data class PetInfoResponse(
    val accountId: Long,
    val petName: String,
    val petGender: String,  // --> 남아/여아 반환
    val petBirth: String,   // --> 2019년 5월생
    val petBreed: String,
    val petNeutered: String,  // --> String 중성화 했어요/중성화 안 했어요
    val petAge: String,  // --> 추가 3살
    val petPhoto: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,  // 변경: readString()으로 바로 읽음
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(accountId)
        parcel.writeString(petName)
        parcel.writeString(petGender)
        parcel.writeString(petBirth)
        parcel.writeString(petBreed)
        parcel.writeString(petNeutered)
        parcel.writeString(petAge)
        parcel.writeString(petPhoto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PetInfoResponse> {
        override fun createFromParcel(parcel: Parcel): PetInfoResponse {
            return PetInfoResponse(parcel)
        }

        override fun newArray(size: Int): Array<PetInfoResponse?> {
            return arrayOfNulls(size)
        }
    }
}