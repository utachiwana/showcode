package com.utachiwana.messenger.dagger

import android.content.Context
import com.utachiwana.messenger.ui.activities.ComposeMainActivity
import com.utachiwana.messenger.ui.fragments.AuthFragment
import com.utachiwana.messenger.ui.fragments.RegisterFragment
import com.utachiwana.messenger.ui.activities.StartActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RoomModule::class])
interface AppComponent {

    fun inject(startActivity: StartActivity)
    fun inject(authFragment: AuthFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(composeMainActivity: ComposeMainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun setContext(context: Context): Builder
    }

}