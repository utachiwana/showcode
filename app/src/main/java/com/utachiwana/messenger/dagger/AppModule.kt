package com.utachiwana.messenger.dagger

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utachiwana.messenger.local.AppSharedPreferences
import com.utachiwana.messenger.network.FConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Auth