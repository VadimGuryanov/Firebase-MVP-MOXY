package kpfu.itis.firebasemvp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kpfu.itis.firebasemvp.presenter.App
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(app : App) : Context = app.applicationContext

}
