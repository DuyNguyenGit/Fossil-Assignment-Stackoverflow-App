package com.fossil.duy.stackoverflow.userdetail.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fossil.duy.stackoverflow.databinding.UserDetailItemBinding
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import timber.log.Timber

class UserDetailsAdapter :
    PagedListAdapter<UserDetailEntity, UserDetailsAdapter.UserDetailViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder =
        UserDetailViewHolder(
            UserDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
        val userDetail: UserDetailEntity? = getItem(position)
        userDetail?.let { holder.bind(it) }
    }

    class UserDetailViewHolder(private val binding: UserDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userDetailEntity: UserDetailEntity) {
            binding.apply {
                userDetail = userDetailEntity
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<UserDetailEntity>() {
    override fun areItemsTheSame(oldItem: UserDetailEntity, newItem: UserDetailEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserDetailEntity, newItem: UserDetailEntity): Boolean =
        oldItem == newItem
}