package com.allforyou.app

data class RemittanceRequest(
    var myAccountId: Long,
    var receiverAccountId : Long,
    var password: String,
    var remittanceAmount : Long
)
object RemittanceRequestManager {
    private var data: RemittanceRequest? = null
    var name: String = ""
    var receiverAccountNumber = ""
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