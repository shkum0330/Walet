package com.allforyou.app

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VersionAdapter : RecyclerView.Adapter<VersionAdapter.VersionVH>() {
    lateinit var versionsList : List<Versions>
//
//    constructor() {
//
//    }
//
//    constructor(versionsList: List<Versions>) {
//        this.versionsList = versionsList
//    }


    class VersionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionVH {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VersionVH, position: Int) {
        TODO("Not yet implemented")
    }
}