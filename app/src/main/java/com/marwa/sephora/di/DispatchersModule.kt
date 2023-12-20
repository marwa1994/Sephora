package com.marwa.sephora.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @MainDispatcher
    @Provides
    @Singleton
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    @Singleton
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    @Singleton
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}
