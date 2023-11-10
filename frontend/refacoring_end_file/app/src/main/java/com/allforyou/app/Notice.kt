package com.allforyou.app

import com.google.gson.annotations.SerializedName
data class NoticeResponse (
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String,
    @SerializedName("data") val data : NoticeResponse.Notice? = null
) {
    data class Notice(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("subTitle") val subTitle: String,
        @SerializedName("content") val content: String,
        @SerializedName("isActive") val isActive: Boolean,
        @SerializedName("bannerImg") val bannerImg: String,
        @SerializedName("registerTime") val registerTime: String,
        @SerializedName("modifyTime") val modifyTime: String
    )
}
object NoticeManager {
    private var data: NoticeResponse.Notice? = null
    public var init : Boolean = false
    fun initData(notice: NoticeResponse.Notice) {
        data = notice
        init = true
    }

    fun getInstance(): NoticeResponse.Notice {
        if (data == null) {
            return NoticeResponse.Notice(0, "", "", "", false, "", "", "")
        }
        return data!!
    }
}