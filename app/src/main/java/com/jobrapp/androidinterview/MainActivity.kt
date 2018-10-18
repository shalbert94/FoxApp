package com.jobrapp.androidinterview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jobrapp.androidinterview.adapters.UserAdapter
import com.jobrapp.androidinterview.adapters.UserListAdapter

/**
 * Hosts the single [RecyclerView] in this app
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //Todo: Uncomment one of the three following functions to test out the callbacks
        getUsers(viewModel)
//        getDefferedUsers(viewModel)
//        getInfiniteList(viewModel)


    }

    /** Populates [RecyclerView] using the getUsers() callback */
    fun getUsers(viewModel: MainViewModel) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = UserAdapter()
        }


        viewModel.usersLiveData.observe(this, Observer {
            val adapter = recyclerView.adapter as UserAdapter
            adapter.addUsers(it!!)
        })
        viewModel.getUsers()
    }

    /** Populates [RecyclerView] using the getDefferedUsers() callback */
    fun getDefferedUsers(viewModel: MainViewModel) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = UserAdapter()
        }

        viewModel.defferedUsersLiveDate.observe(this, Observer {
            val adapter = recyclerView.adapter as UserAdapter
            adapter.addUsers(it!!)
        })
        viewModel.getDefferedUsers()
    }

    /** Populates [RecyclerView] using the getInfiniteList() callback */
    fun getInfiniteList(viewModel: MainViewModel) {
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
