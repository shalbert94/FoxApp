package com.jobrapp.androidinterview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jobrapp.server.User

class MainViewModel: ViewModel() {
    val usersLiveData = MutableLiveData<List<User>>()

    fun getUsers() {
        MockServerRepository().getUsers(usersLiveData)
    }
}