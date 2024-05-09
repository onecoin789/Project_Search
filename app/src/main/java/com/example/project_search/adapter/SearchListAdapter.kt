package com.example.project_search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_search.R
import com.example.project_search.data.KakaoImageData
import com.example.project_search.databinding.RvItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchListAdapter : ListAdapter<KakaoImageData, SearchListAdapter.ListHolder>(diffUtil) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null


    inner class ListHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(item: KakaoImageData) {
            with(binding) {
                Glide.with(itemView.context).load(item.thumbnailUrl).into(ivItem)
                tvItemSite.text = item.siteName
                tvItemTime.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(OffsetDateTime.parse(item.datetime))

                if (item.isliked) {
                    ivLiked.setImageResource(R.drawable.heart2)
                } else {
                    ivLiked.setImageDrawable(null)
                }

                itemView.setOnClickListener {
                    itemClick?.onClick(it, adapterPosition)
                }
            }
        }
    }
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<KakaoImageData>() {
            override fun areItemsTheSame(
                oldItem: KakaoImageData,
                newItem: KakaoImageData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: KakaoImageData,
                newItem: KakaoImageData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }




}