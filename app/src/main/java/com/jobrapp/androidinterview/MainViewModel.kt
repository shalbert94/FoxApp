package com.jobrapp.androidinterview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.jobrapp.server.Server
import com.jobrapp.server.User

class MainViewModel: ViewModel() {
    //Traditional Retrofit callback
    val usersLiveData = MutableLiveData<List<User>>()
    fun getUsers() {
        MockServerRepository.getUsers(usersLiveData)
    }

    //Coroutine Retrofit callback
    val defferedUsersLiveDate = MutableLiveData<List<User>>()
    fun getDefferedUsers() {
        MockServerRepository.getDefferedUsers(defferedUsersLiveDate)
    }

    //Infinite list
    lateinit var userPagedList: LiveData<PagedList<User>>
    lateinit var liveDataSource: LiveData<PageKeyedDataSource<Int, User>>

    init {
        val userDataSourceFactory = UserDataSourceFactory()
        liveDataSource = userDataSourceFactory.userLiveDataSource

        userPagedList = LivePagedListBuilder(userDataSourceFactory, Server.PAGE_SIZE).build()
    }
}