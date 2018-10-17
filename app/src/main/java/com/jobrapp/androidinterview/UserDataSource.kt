package com.jobrapp.androidinterview

import android.arch.paging.PageKeyedDataSource
import com.jobrapp.server.Server
import com.jobrapp.server.User

class UserDataSource: PageKeyedDataSource<Int, User>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        val users = MockServerRepository.getInfiniteList()
        callback.onResult(users, null, 2)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val users = MockServerRepository.getInfiniteList()
        val key = if (params.key > 1) params.key - 1 else null
        callback.onResult(users, key)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val users = MockServerRepository.getInfiniteList()
        val key = params.key + 1
        callback.onResult(users, key)
    }
}