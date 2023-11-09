package com.allforyou.app

data class Notice(
    val id : Int,
    val title : String,
    val subTitle : String,
    val content : String,
    val isActive : Boolean,
    val bannerImg : String,
    val registerTime : String,
    val modifyTime : String
)
object NoticeManager {
    private var data: Notice? = null
    public var init : Boolean = false
    fun initData(notice: Notice) {
        data = notice
        init = true
    }

    fun getInstance(): Notice {
        if (data == null) {
            return Notice(0,"","","",false,"","","")
        }
        return data!!
    }
}