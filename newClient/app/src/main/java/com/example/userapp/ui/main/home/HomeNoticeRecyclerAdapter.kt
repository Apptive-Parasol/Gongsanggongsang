package com.example.userapp.ui.main.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.data.model.PostDataInfo
import com.example.userapp.databinding.FragmentMainhomeHomePartItemBinding
import java.time.LocalDate
import java.time.LocalTime

class HomeNoticeRecyclerAdapter(var postDataList : ArrayList<PostDataInfo>): RecyclerView.Adapter<HomeNoticeRecyclerAdapter.HomeNoticePartViewHolder>() {
    override fun getItemCount(): Int {
        return postDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNoticePartViewHolder {
        val viewbinding = FragmentMainhomeHomePartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  HomeNoticePartViewHolder(viewbinding, parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HomeNoticePartViewHolder, position: Int) {
        holder.bind(postDataList[position])

    }

    fun getItem(position: Int): PostDataInfo {
        return postDataList[position]
    }

    inner class HomeNoticePartViewHolder(viewbinding: FragmentMainhomeHomePartItemBinding, itemview: ViewGroup) : RecyclerView.ViewHolder(viewbinding.root) {
        val binding = viewbinding

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(it: PostDataInfo) {
            binding.run {
                homeNoticePartItemTitle.text = it.post_title
                homeNoticePartItemCategory.text = it.post_state
                val postDateNow: String = LocalDate.now().toString()
                val postTimeNow: String = LocalTime.now().toString()

                if(it.post_date == postDateNow){
                    val hour = postTimeNow.substring(0,2).toInt() - it.post_time.substring(0,2).toInt()
                    val minute = postTimeNow.substring(3,5).toInt() - it.post_time.substring(3,5).toInt()
                    if(hour == 0){ homeNoticePartItemDate.text = "${minute}분 전" }
                    else{ homeNoticePartItemDate.text = "${hour}시간 전" }
                }
                else{ homeNoticePartItemDate.text = it.post_date.substring(5) }
            }
        }
    }

}