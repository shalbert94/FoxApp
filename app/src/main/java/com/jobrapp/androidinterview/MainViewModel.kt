package com.jobrapp.androidinterview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.jobrapp.server.Server
import com.jobrapp.server.User
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * Belongs to [MainActivity]
 */
class MainViewModel : ViewModel() {
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
    val userPagedList: LiveData<PagedList<User>>

    init {
        userPagedList = LivePagedListBuilder(dataSourceFactory(), Server.PAGE_SIZE).build()
    }

    private fun dataSourceFactory() = object : DataSource.Factory<Int, User>() {
        override fun create(): DataSource<Int, User> {
            return pageKeyedDataSource()
        }
    }

    private fun pageKeyedDataSource() = object : PageKeyedDataSource<Int, User>() {
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) = runBlocking {
            val users = MockServerRepository.getInfiniteList().await()
            callback.onResult(users, null, 2)
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = runBlocking {
            val users = MockServerRepository.getInfiniteList().await()
            val key = if (params.key > 1) params.key - 1 else null
            callback.onResult(users, key)
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = runBlocking {
            val users = MockServerRepository.getInfiniteList().await()
            val key = params.key + 1
            callback.onResult(users, key)
        }

    }
}