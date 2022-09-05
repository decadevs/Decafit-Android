package com.decagon.decafit.common.common.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.decagon.decafit.common.common.data.local_data.DecafitDAO
import com.decagon.decafit.common.common.data.local_data.LocalDataBase
import com.decagon.decafit.common.common.data.networks.NetworkConstant.BASE_URL1
import com.decagon.decafit.common.common.domain.repository.RepositoryImp
import com.decagon.decafit.common.common.domain.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkApi(@ApplicationContext context: Context) : ApolloClient{
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logger)
            .build()

        val instance = ApolloClient.Builder()
            .serverUrl(BASE_URL1 )
            .okHttpClient(okhttpClient)
            .build()
        return instance
    }

    @Singleton
    @Provides
    fun provideRepositoryInterface (
        repositoryImp: RepositoryImp
    ): RepositoryInterface {
        return repositoryImp
    }

    @Singleton
    @Provides
    fun provideLocalDatabase(app: Application): LocalDataBase {
        return Room.databaseBuilder(app, LocalDataBase::class.java, "exercise_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideDecafitDAO (
        localDataBase: LocalDataBase
    ): DecafitDAO {
        return localDataBase.decafitDAO()
    }

}