package com.zaloracasestudy.catastrophic.di

import com.zaloracasestudy.catastrophic.BuildConfig
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListApi
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSource
import com.zaloracasestudy.catastrophic.data.repository.remote.CatListRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("content-type", "application/json;charset=utf-8")
                .build()
            it.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesCatListApi(retrofit: Retrofit): CatListApi =
        retrofit.create(CatListApi::class.java)
/*
    @Provides
    @Singleton
    fun providesCatListApiImpl(@ApplicationContext context: Context, gson: Gson)
            : CatListApi = CatListApiImpl(context, gson)

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()*/

    @Provides
    @Singleton
    fun providesCatListRemoteDataSourceImpl(catListApi: CatListApi): CatListRemoteDataSource =
        CatListRemoteDataSourceImpl(catListApi)

}