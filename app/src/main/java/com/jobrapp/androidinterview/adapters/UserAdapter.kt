package com.jobrapp.androidinterview.adapters

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
class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var users: List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
            UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,
                    false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name.text = users[position].name

        val context = holder.profilePic.context
        Glide.with(context).load(users[position].profile_url).into(holder.profilePic)
    }

    fun addUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.nameTextView
        val profilePic: ImageView = itemView.profilePic
    }
}