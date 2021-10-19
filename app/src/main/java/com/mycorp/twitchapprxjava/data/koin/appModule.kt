package com.mycorp.twitchapprxjava.data.koin

import com.mycorp.twitchapprxjava.data.network.NetworkController
import com.mycorp.twitchapprxjava.data.network.NetworkControllerImpl
import com.mycorp.twitchapprxjava.data.network.retrofit.ApiService
import com.mycorp.twitchapprxjava.data.network.retrofit.ServerApi
import com.mycorp.twitchapprxjava.data.repository.RepositoryImplementation
import com.mycorp.twitchapprxjava.data.storage.Storage
import com.mycorp.twitchapprxjava.data.storage.room.RoomStorage
import com.mycorp.twitchapprxjava.domain.repository.Repository
import com.mycorp.twitchapprxjava.domain.use_cases.GetFromDbUseCase
import com.mycorp.twitchapprxjava.domain.use_cases.GetFromServerUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<NetworkController> { NetworkControllerImpl(get()) }

    single<Storage> { RoomStorage(androidContext()) }

    single<Repository> { RepositoryImplementation(get(), get()) }

    single { GetFromServerUseCase(get()) }

    single { GetFromDbUseCase(get()) }

    single { provideRetrofit() }

    single { provideRetrofitService(get()) }
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ServerApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

private fun provideRetrofitService(retrofit: Retrofit): ApiService{
    return retrofit.create(ApiService::class.java)
}