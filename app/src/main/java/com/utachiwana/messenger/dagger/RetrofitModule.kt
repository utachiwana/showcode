package com.utachiwana.messenger.dagger

import com.utachiwana.messenger.data.network.RetrofitApi
import com.utachiwana.messenger.data.network.WebConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient().newBuilder().addInterceptor { chain ->
        val url =
            chain.request().url.newBuilder().addQueryParameter("appid", WebConfig.APPID).build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(WebConfig.URL_CITY)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)

}