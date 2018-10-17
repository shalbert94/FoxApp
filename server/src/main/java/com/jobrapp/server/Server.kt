package com.jobrapp.server

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Fake server to return json responses
 */
class Server {
    private val mockWebServer = MockWebServer()
    private val apiService : ApiService
    companion object {
        private val randomImages = Random(Date().time)
        private val randomNames = Random(Date().time)
        const val IMAGEURL = "http://randomfox.ca/images/"
        const val NUM_IMAGES = 121
        const val NUM_NAMES = 7
        const val PAGE_SIZE = 20
        private val names = listOf(
            "Sammy Fox",
            "Suzzy Fox",
            "Claire Fox",
            "Tom Fox",
            "Jeremy Fox",
            "Clark Fox",
            "Mark Fox",
            "Freddy Fox"
        )
    }

    init {
        mockWebServer.start()
        enqueueResponse("http/users_response.json")

        val baseUrl = mockWebServer.url("").url().toString()

        val retrofit = buildRetrofit(baseUrl)
        apiService = retrofit.create(ApiService::class.java)

    }

    fun getInfiniteList() : List<User> {
        val users = ArrayList<User>()
        for (i in 0..PAGE_SIZE) {
            val randomImageNumber = randomImages.nextInt(NUM_IMAGES)
            val randomNameNumber = randomNames.nextInt(NUM_NAMES)
            val user = User(names[randomNameNumber], IMAGEURL + randomImageNumber.toString() + ".jpg")
            users.add(user)
        }
        return users
    }

    fun getDeferredUsers() : Deferred<List<User>> {
        return apiService.getDeferredUsers()
    }

    fun getUsers() : Call<List<User>> {
        return apiService.getUsers()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    private fun buildRetrofit(baseUrl: String): Retrofit {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun enqueueResponse(bodyFileName: String, statusCode: Int = 200) {
        val resource = this::class.java.classLoader.getResource(bodyFileName)
        val body = resource.readText()
        mockWebServer.enqueue(
            MockResponse().setResponseCode(statusCode).setBody(body))
    }
}