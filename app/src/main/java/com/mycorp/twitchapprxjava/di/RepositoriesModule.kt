package com.mycorp.twitchapprxjava.di

import com.mycorp.twitchapprxjava.repository.GamesRepository
import com.mycorp.twitchapprxjava.repository.GamesRepositoryImplementation
import com.mycorp.twitchapprxjava.repository.Repository
import com.mycorp.twitchapprxjava.repository.RepositoryImplementation
import org.koin.dsl.module

val repositoriesModule = module {
    single<Repository> { RepositoryImplementation(get(), get()) }
    single<GamesRepository> { GamesRepositoryImplementation(get(), get()) }
}