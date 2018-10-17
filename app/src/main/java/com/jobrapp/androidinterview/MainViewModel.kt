package com.jobrapp.androidinterview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
}