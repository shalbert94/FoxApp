package com.jobrapp.androidinterview

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.jobrapp.server.Server
import com.jobrapp.server.User
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

/**
 * Interacts with [Server]
 */
object MockServerRepository {
    private val TAG = MockServerRepository::class.java.simpleName

    private val server = async { Server() }

    /**
     * Used coroutines here because it was the simplest way to initialize [Server], which causes
     * a networkOnMainThreadException whe not initialized asynchronously
     */
    fun getUsers(liveData: MutableLiveData<List<User>>) = launch {
        server.await().getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                when (response.isSuccessful) {
                    true -> liveData.postValue(response.body())
                    false -> Log.e(TAG, "Request error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "createAccessToken failed-- Cause: ${t.cause} \t Message: ${t.message}")
                throw t
            }
        })
    }

    fun getDefferedUsers(liveData: MutableLiveData<List<User>>) = launch {
        try {
            val result = server.await().getDeferredUsers().await()
            liveData.postValue(result)
        } catch (e: HttpException) {
            //Catch Http errors. Equivalent to "!response.isSuccessful"
            Log.e(TAG, "HttpException code: ${e.code()}")
        } catch (e: Throwable) {
            //Catch all other exceptions. Equivalent to "onFailure()"
            Log.e(TAG, "getDefferedUsers() failed-- Cause: ${e.cause} \t Message: ${e.message}")
            throw e
        }

    }

     fun getInfiniteList(): Deferred<List<User>> = async {
         server.await().getInfiniteList()
    }

}