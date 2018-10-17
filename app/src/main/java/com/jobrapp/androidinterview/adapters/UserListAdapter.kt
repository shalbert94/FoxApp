package com.jobrapp.androidinterview.adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jobrapp.androidinterview.R
import com.jobrapp.server.User
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Adapter for [MainActivity]'s [RecyclerView]
 */
class UserListAdapter : PagedListAdapter<User, UserListAdapter.UserListViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem?.name == newItem?.name
                        && oldItem?.profile_url == newItem?.profile_url
            }

            override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
            UserListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,
                    false))

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = getItem(position) ?: return

        holder.name.text = user.name

        val context = holder.profilePic.context
        Glide.with(context).load(user.profile_url).into(holder.profilePic)

    }

    class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.nameTextView
        val profilePic: ImageView = itemView.profilePic
    }
}