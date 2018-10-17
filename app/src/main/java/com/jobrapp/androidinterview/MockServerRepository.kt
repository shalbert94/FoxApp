package com.jobrapp.androidinterview

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.jobrapp.server.Server
import com.jobrapp.server.User
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Interacts with [Server]
 */
class MockServerRepository {
    private val TAG = MockServerRepository::class.java.simpleName

    val server = Server()

    fun getUsers(liveData: MutableLiveData<List<User>>) = launch(UI) {
        server.getUsers().enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                when (response.isSuccessful) {
                    true -> liveData.postValue(response.body())
                    false -> Log.e(TAG,"Request error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "createAccessToken failed-- Cause: ${t.cause} \t Message: ${t.message}")
                throw t
            }
        })
    }
}