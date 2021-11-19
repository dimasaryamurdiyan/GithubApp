package com.trial.github_android.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.ItemFavoriteBinding
import com.trial.github_android.databinding.ItemFollowingBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){
    private val items = ArrayList<UserEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<UserEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root){

        private lateinit var user: UserEntity

        @SuppressLint("SetTextI18n")
        fun bind(item: UserEntity) {
            this.user = item
            binding.name.text = item.login
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .transform(CircleCrop())
                .into(binding.image)
        }


    }
}