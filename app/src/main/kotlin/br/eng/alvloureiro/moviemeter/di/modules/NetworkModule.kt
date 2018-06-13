package br.eng.alvloureiro.moviemeter.di.modules

import android.support.v7.app.AppCompatActivity
import br.eng.alvloureiro.moviemeter.BuildConfig
import br.eng.alvloureiro.moviemeter.BuildConfig.BASE_API_URL
import br.eng.alvloureiro.moviemeter.api.Api
import br.eng.alvloureiro.moviemeter.ext.app
import br.eng.alvloureiro.moviemeter.okhttp.HttpCacheInterceptor
import br.eng.alvloureiro.moviemeter.okhttp.HttpOfflineCacheInterceptor
import br.eng.alvloureiro.moviemeter.okhttp.HttpQueryInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule(private val activity: AppCompatActivity) {
    private companion object {
        const val CACHE_SIZE = 10 * 1024 * 1024 //10MB
        const val OKHTTP_READ_TIMEOUT = 40
        const val OKHTTP_WRITE_TIMEOUT = 40
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesCache(): Cache {
        val cacheDir = File(activity.app.cacheDir, "http-cache")
        return Cache(cacheDir, CACHE_SIZE.toLong())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpOfflineCacheInterceptor(activity.app))
                .addInterceptor(HttpQueryInterceptor())
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(
                                if (BuildConfig.DEBUG) {
                                    HttpLoggingInterceptor.Level.BODY
                                } else {
                                    HttpLoggingInterceptor.Level.NONE
                                }
                        ))
                .addNetworkInterceptor(HttpCacheInterceptor())
                .readTimeout(OKHTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(OKHTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .cache(cache)
                .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_API_URL)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun providesMoviesDatabaseApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}