package com.brandsin.driver.network

import com.brandsin.driver.BuildConfig
import com.brandsin.driver.database.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//const val BASE_URL = "https://backend.elmaredegypt.com"

//const val BASE_URL = "https://hagaty-app.com"
const val BASE_URL = "https://backend.brandsin.net"
/*
*add this if you want a static header in all requests
**/
fun getHeaderInterceptor(): Interceptor {
    return object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request =
                chain.request().newBuilder()
                    .build()
            return chain.proceed(request)
        }
    }
}

private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .apply {
            if(BuildConfig.DEBUG){
                this.addInterceptor(HttpLoggingInterceptor()
                    .setLevel(Level.BODY))
            }
        }
        .readTimeout(70, TimeUnit.SECONDS)
        .connectTimeout(70, TimeUnit.SECONDS)
        .writeTimeout(70, TimeUnit.SECONDS)
        .build()
}

private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
   // .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(createOkHttpClient())
    .build()

object RetrofitBuilder {
    val API_SERVICE: ApiInterface by lazy {
        retrofitBuilder.create(ApiInterface::class.java)
    }
}



