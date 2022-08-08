package com.utachiwana.messenger

import android.app.Application
import android.content.Context
import com.utachiwana.messenger.dagger.AppComponent
import com.utachiwana.messenger.dagger.DaggerAppComponent
import com.utachiwana.messenger.data.local.AppSharedPreferences

class MessengerApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().setContext(this).build()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MessengerApp -> appComponent
        else -> this.applicationContext.appComponent
    }


