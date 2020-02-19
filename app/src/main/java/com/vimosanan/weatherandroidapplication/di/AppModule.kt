package com.vimosanan.weatherandroidapplication.di
import com.vimosanan.weatherandroidapplication.app.Constants
import com.vimosanan.weatherandroidapplication.network.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    private var cacheSize = 10 * 1024 * 1024 // 10 MB
    var cache: Cache = Cache(getCacheDir(), cacheSize.toLong())

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): ApiInterface {
        return retrofit
            .create(ApiInterface::class.java)
    }



}