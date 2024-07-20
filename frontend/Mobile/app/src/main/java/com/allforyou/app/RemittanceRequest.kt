package com.allforyou.app

data class RemittanceRequest(
    var myAccountId: Long,
    var receiverAccountId : Long,
    var password: String,
    var remittanceAmount : Long,
)
object RemittanceRequestManager {
    private var data: RemittanceRequest? = null
    var name: String = ""
        var rfid:String=""
    var linkedAccountNumber:String=""
    var accountNumber:String = ""
    var paymentId:Long=0L
    var linkedAccountId=0L
    var transferId:Long=0L

    fun initData(request: RemittanceRequest) {
        data = request
    }

    fun getInstance(): RemittanceRequest {
        if (data == null) {
            data = RemittanceRequest(0,0,"", 0)
        }
        return data!!
    }
}