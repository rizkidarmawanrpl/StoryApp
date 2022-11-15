package com.erdeprof.storyapp.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.data.Story

class ListStoryPagerAdapter: PagingDataAdapter<Story, ListStoryPagerAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(item: Story) {
            val (lon, lat, createdAt, photoUrl, description, name, id) = item
            tvName.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (lon, lat, createdAt, photoUrl, description, name, id) = getItem(position) // listStory[position]
//        Glide.with(holder.itemView.context)
//            .load(photoUrl)
//            .placeholder(R.mipmap.ic_launcher)
//            .error(R.mipmap.ic_launcher)
//            .into(holder.imgPhoto)
//        holder.tvName.text = name
//        holder.tvDescription.text = description
//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(getItem(position)/*listStory[holder.adapterPosition]*/) }

        // getItem(position)?.let { holder.bind(it) }

        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    // override fun getItemCount(): Int = listStory.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Story)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}