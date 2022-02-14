package com.test.gittrendingrepo.di

import androidx.room.Room
import com.test.gittrendingrepo.BuildConfig
import com.test.gittrendingrepo.data.local.LocalDataStorage
import com.test.gittrendingrepo.data.local.LocalDataStorageImpl
import com.test.gittrendingrepo.data.local.room.REPO_DATABASE
import com.test.gittrendingrepo.data.local.room.RepoDetailDatabase
import com.test.gittrendingrepo.data.network.GitHubApi
import com.test.gittrendingrepo.provider.RepoProvider
import com.test.gittrendingrepo.provider.RepoProviderImpl
import com.test.gittrendingrepo.ui.repo.detail.RepoDetailViewModel
import com.test.gittrendingrepo.ui.repo.list.RepoListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val viewModels = module {
    viewModel { (repoId: Int) -> RepoDetailViewModel(repoId, get()) }
    viewModel { RepoListViewModel(get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideGitHubApi(get()) }
    single { provideRetrofit(get()) }
}

val persistenceModule = module {
    single {
        Room.databaseBuilder(androidApplication(), RepoDetailDatabase::class.java, REPO_DATABASE)
            .build()
    }
    single { get<RepoDetailDatabase>().repoDao() }

    single<LocalDataStorage> { LocalDataStorageImpl(get()) }
}

val providerModule = module {
    single<RepoProvider> { RepoProviderImpl(get(), get()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)
