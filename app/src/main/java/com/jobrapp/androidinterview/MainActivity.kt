package com.jobrapp.androidinterview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jobrapp.androidinterview.adapters.UserAdapter
import com.jobrapp.androidinterview.adapters.UserListAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //Applies to getUsers() and getDefferedUsers()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = UserAdapter()
//        }

        //Todo: Place in a separate method

//        viewModel.usersLiveData.observe(this, Observer {
//            val adapter = recyclerView.adapter as UserAdapter
//            adapter.addUsers(it!!)
//        })
//        viewModel.getUsers()


//        viewModel.defferedUsersLiveDate.observe(this, Observer {
//            val adapter = recyclerView.adapter as UserAdapter
//            adapter.addUsers(it!!)
//        })
//        viewModel.getDefferedUsers()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = UserListAdapter()
        }

        viewModel.userPagedList.observe(this, Observer {
            val adapter = recyclerView.adapter as UserListAdapter
            adapter.submitList(it)
        })

    }

}
