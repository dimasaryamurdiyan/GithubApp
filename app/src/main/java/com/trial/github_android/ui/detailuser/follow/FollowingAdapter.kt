package com.trial.github_android.ui.detailuser.follow

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.remote.response.UserResponse.User
import com.trial.github_android.databinding.ItemFollowingBinding
import com.trial.github_android.databinding.ItemUserBinding

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>(){
    private val items = ArrayList<FollowingEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<FollowingEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.FollowingViewHolder {
        val binding: ItemFollowingBinding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FollowingViewHolder(private val binding: ItemFollowingBinding): RecyclerView.ViewHolder(binding.root){

        private lateinit var user: FollowingEntity

        @SuppressLint("SetTextI18n")
        fun bind(item: FollowingEntity) {
            this.user = item
            binding.tvUserName.text = item.login
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .transform(CircleCrop())
                .into(binding.image)
        }


    }
}
