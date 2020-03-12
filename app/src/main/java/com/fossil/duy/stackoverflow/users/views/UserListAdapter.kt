package com.fossil.duy.stackoverflow.users.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fossil.duy.stackoverflow.databinding.UserItemBinding
import com.fossil.duy.stackoverflow.users.models.UserDomain

class UserListAdapter : ListAdapter<UserDomain, UserListAdapter.UserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: UserDomain = getItem(position)
        holder.bind(createClickListener(user.id, user.name), user)
    }

    private fun createClickListener(id: Long, name: String): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(id, name)
            it.findNavController().navigate(direction)
        }
    }

    class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, userDomain: UserDomain) {
            binding.apply {
                clickListener = listener
                user = userDomain
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<UserDomain>() {
    override fun areItemsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean =
        oldItem == newItem
}