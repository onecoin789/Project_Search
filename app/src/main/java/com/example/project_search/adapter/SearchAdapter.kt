package com.example.project_search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_search.R
import com.example.project_search.data.KakaoImageData
import com.example.project_search.databinding.RvItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(private var itemList: MutableList<KakaoImageData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = itemList[position]
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(currentItem)

                if (itemList[position].isliked) {
                    holder.ivLiked.setImageResource(R.drawable.heart2)
                } else {
                    holder.ivLiked.setImageResource(0)
                }
                holder.itemView.setOnClickListener {
                    itemClick?.onClick(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ItemViewHolder(
        private val binding: RvItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val ivItem = binding.ivItem
        val tvItemSite = binding.tvItemSite
        val tvItemTime = binding.tvItemTime
        val ivLiked = binding.ivLiked

        @SuppressLint("NewApi")
        fun bind(item: KakaoImageData) {
            Glide.with(itemView.context).load(item.thumbnailUrl).into(ivItem)
            tvItemSite.text = item.siteName
            tvItemTime.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(OffsetDateTime.parse(item.datetime))
        }
    }
    fun updateList(newDataList: MutableList<KakaoImageData>) {
        itemList = newDataList
        notifyDataSetChanged()
    }

}