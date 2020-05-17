package kpfu.itis.firebasemvp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kpfu.itis.firebasemvp.presenter.App
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(app : App) : Context = app.applicationContext

}
