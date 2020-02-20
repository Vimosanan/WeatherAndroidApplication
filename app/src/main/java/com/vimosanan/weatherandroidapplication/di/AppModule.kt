package com.vimosanan.weatherandroidapplication.di
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vimosanan.weatherandroidapplication.app.Constants
import com.vimosanan.weatherandroidapplication.network.ApiInterface
import com.vimosanan.weatherandroidapplication.persistence.WeatherDataDao
import com.vimosanan.weatherandroidapplication.persistence.WeatherDataDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): ApiInterface {
        return retrofit
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDataDatabase {
        return Room
            .databaseBuilder(app, WeatherDataDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: WeatherDataDatabase): WeatherDataDao {
        return db.weatherDataDao()
    }

}