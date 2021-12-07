package com.trial.github_android.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.ItemFavoriteBinding
import com.trial.github_android.databinding.ItemFollowingBinding
import com.trial.github_android.ui.user.UserAdapter

class FavoriteAdapter(private val listener: FavoriteAdapter.UserItemListener): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){
    interface UserItemListener {
        fun onClicked(username: String)
    }
    private val items = ArrayList<UserEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<UserEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding, private val listener: FavoriteAdapter.UserItemListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }
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

        override fun onClick(p0: View?) {
            user.login?.let { listener.onClicked(it) }
        }


    }
}