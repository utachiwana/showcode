package com.utachiwana.messenger.dagger

import android.content.Context
import androidx.room.Room
import com.utachiwana.messenger.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, "app_db").build()

}