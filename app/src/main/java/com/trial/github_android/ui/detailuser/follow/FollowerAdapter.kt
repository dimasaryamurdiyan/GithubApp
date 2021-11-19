package com.trial.github_android.ui.detailuser.follow

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.trial.github_android.data.entities.FollowersEntity
import com.trial.github_android.data.remote.response.UserResponse.User
import com.trial.github_android.databinding.ItemFollowersBinding
import com.trial.github_android.databinding.ItemFollowingBinding

class FollowerAdapter: RecyclerView.Adapter<FollowerViewHolder>(){
    private val items = ArrayList<FollowersEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<FollowersEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding = ItemFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        return holder.bind(items[position])
    }
}

class FollowerViewHolder(private val binding: ItemFollowersBinding): RecyclerView.ViewHolder(binding.root){

    private lateinit var user: FollowersEntity

    @SuppressLint("SetTextI18n")
    fun bind(item: FollowersEntity) {
        this.user = item
        binding.tvUserName.text = item.login
        Glide.with(binding.root)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(binding.image)
    }


}