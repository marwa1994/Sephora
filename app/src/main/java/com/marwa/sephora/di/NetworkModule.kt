package com.marwa.sephora.di


import android.app.Application
import com.marwa.sephora.data.utils.ApiURL
import com.marwa.sephora.data.utils.ApiURL.TIME_OUT
import com.marwa.sephora.data.service.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideBaseURL() = ApiURL.BASE_URL

    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideNetworkCacheInterceptor() = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(15, TimeUnit.MINUTES) // Set the max age for the cache
            .build()
        response.newBuilder()
            .header(ApiURL.CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        application: Application,
        networkCacheInterceptor: Interceptor
    ): OkHttpClient {

        return OkHttpClient().newBuilder().apply {
            callTimeout(TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            cache(Cache(File(application.cacheDir, ApiURL.HTTP_CACHE), ApiURL.MAX_SIZE))
            addNetworkInterceptor(networkCacheInterceptor)
            addInterceptor(loggingInterceptor)
            build()
        }.build()
    }

    /**
     * Provides Moshi
     */
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}