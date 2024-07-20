package com.allforyou.app

import android.os.Parcel
import android.os.Parcelable

data class TransactionLogResponse (
    val message: String,
    val code: String,
    val data : List<TransactionLog>? = null
){
    data class TransactionLog (
        val id : Long,
        val recipient : String,

        val transactionType : String,
        val paymentAccount : Long,
        val balance : Long,
        val transactionDate : String
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString() ?: ""
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeLong(id)
            parcel.writeString(recipient)
            parcel.writeString(transactionType)
            parcel.writeLong(paymentAccount)
            parcel.writeLong(balance)
            parcel.writeString(transactionDate)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<TransactionLog> {
            override fun createFromParcel(parcel: Parcel): TransactionLog {
                return TransactionLog(parcel)
            }

            override fun newArray(size: Int): Array<TransactionLog?> {
                return arrayOfNulls(size)
            }
        }
    }
}
