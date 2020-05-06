package io.github.mattpvaughn.pokeapp.injection.modules

import android.app.Application
import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import io.github.mattpvaughn.pokeapp.BuildConfig
import io.github.mattpvaughn.pokeapp.application.APP_NAME
import io.github.mattpvaughn.pokeapp.application.LOG_NETWORK_REQUESTS
import io.github.mattpvaughn.pokeapp.data.local.*
import io.github.mattpvaughn.pokeapp.data.remote.*
import io.github.mattpvaughn.pokeapp.features.library.LibraryViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        app.getSharedPreferences(APP_NAME, MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePrefsRepo(prefsImpl: SharedPreferencesPrefsRepo): PrefsRepo = prefsImpl

    @Provides
    @Singleton
    fun provideModelDao(): ModelDao = getModelDatabase(app.applicationContext).modelDao

    @Provides
    @Singleton
    fun provideInternalDeviceDirs(): File = app.applicationContext.filesDir

    @Provides
    @Singleton
    fun provideExternalDeviceDirs(): List<File> =
        ContextCompat.getExternalFilesDirs(app.applicationContext, null).toList()

//    @Provides
//    @Singleton
//    fun workManager(): WorkManager = WorkManager.getInstance(app)

    @Provides
    @Singleton
    fun downloadManager(): DownloadManager =
        app.getSystemService(Service.DOWNLOAD_SERVICE) as DownloadManager

    @Provides
    @Singleton
    fun loggingInterceptor() =
        if (BuildConfig.DEBUG && LOG_NETWORK_REQUESTS) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun remoteService(retrofit: Retrofit): PokemonAPIService = retrofit.create(PokemonAPIService::class.java)

    @Provides
    @Singleton
    fun libraryViewModelFactory(pokemonAPIService: PokemonAPIService) = LibraryViewModelFactory(pokemonAPIService)

}