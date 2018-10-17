package com.jobrapp.server

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ServerTest {
    @Test fun getDeferredUsers() {
        val server = Server()
        runBlocking {
            val users = server.getDeferredUsers().await()
            assertNotNull(users)
            assertEquals(1, users.size)
        }
    }
    @Test fun getUsers() {
        val server = Server()
        runBlocking {
            val call = server.getUsers()
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    fail()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val users = response.body()
                    assertNotNull(users)
                    assertEquals(1, users?.size)
                }

            })
        }
    }
}