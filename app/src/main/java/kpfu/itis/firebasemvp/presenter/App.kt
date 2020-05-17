package kpfu.itis.firebasemvp.presenter

import android.app.Application
import kpfu.itis.firebasemvp.di.Injector
import moxy.MvpFacade
import okhttp3.Route
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        Injector.init(this)
    }

}