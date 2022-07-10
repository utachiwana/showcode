package com.utachiwana.messenger.dagger

import android.content.Context
import com.utachiwana.messenger.main.MainActivity
import com.utachiwana.messenger.start.fragments.AuthFragment
import com.utachiwana.messenger.start.fragments.RegisterFragment
import com.utachiwana.messenger.start.StartActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(startActivity: StartActivity)
    fun inject(authFragment: AuthFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun setContext(context: Context): Builder
    }

}