package com.allforyou.app

import com.google.gson.annotations.SerializedName
data class NoticeResponse (
    val message: String,
    val code: String,
    val data : NoticeResponse.Notice? = null
) {
    data class Notice(
        val id: Int,
        val title: String,
        val subTitle: String,
        val content: String,
        val isActive: Boolean,
        val bannerImg: String,
        val registerTime: String,
        val modifyTime: String
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