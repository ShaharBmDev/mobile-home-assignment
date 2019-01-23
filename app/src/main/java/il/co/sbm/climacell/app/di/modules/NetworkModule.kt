package il.co.sbm.climacell.app.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import il.co.sbm.climacell.BuildConfig
import il.co.sbm.climacell.app.di.AppContext
import il.co.sbm.climacell.app.di.AppScope
import il.co.sbm.climacell.app.network.clients.RetrofitClientFactory
import il.co.sbm.climacell.app.network.services.climacell.ClimaCellService
import il.co.sbm.climacell.app.network.services.restcountries.RestCountriesService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = [AppModule::class])
class NetworkModule {

    companion object {
        private const val MAX_CACHE_SIZE: Long = 10 * 1024 * 1024
        private const val CACHE_FILE_NAME = "okhttp_cache"
        private const val TIMEOUT_SECONDS: Long = 5
    }

    @Provides
    @AppScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.i(message) })
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @AppScope
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, MAX_CACHE_SIZE)
    }

    @Provides
    @AppScope
    fun cacheFile(@AppContext context: Context): File {
        return File(
            context.cacheDir,
            CACHE_FILE_NAME
        )
    }

    @Provides
    @AppScope
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @AppScope
    fun restCountriesService(iOkHttpClient: OkHttpClient): RestCountriesService {
        return RetrofitClientFactory.createClient(iOkHttpClient, RestCountriesService.BASE_URL)
            .create(RestCountriesService::class.java)
    }

    @Provides
    @AppScope
    fun climaCellService(iOkHttpClient: OkHttpClient): ClimaCellService {
        return RetrofitClientFactory.createClient(iOkHttpClient, ClimaCellService.BASE_URL)
            .create(ClimaCellService::class.java)
    }
}