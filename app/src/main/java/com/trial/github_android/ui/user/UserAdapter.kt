package com.trial.github_android.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.ItemUserBinding

class UserAdapter (private val listener: UserItemListener): RecyclerView.Adapter<UserViewHolder>(){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class UserViewHolder(private val binding: ItemUserBinding, private val listener: UserAdapter.UserItemListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.root.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        user.login?.let { listener.onClicked(it) }
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


}
