package com.jobrapp.androidinterview

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.jobrapp.server.User

class UserDataSourceFactory: DataSource.Factory<Int, User>() {

    val userLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, User>>()

    override fun create(): DataSource<Int, User> {
        val userDataSource = UserDataSource()
        userLiveDataSource.postValue(userDataSource)
        return userDataSource
    }

}