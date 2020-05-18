package kpfu.itis.firebasemvp.presenter

import android.app.Application
import kpfu.itis.firebasemvp.di.Injector
import moxy.MvpFacade

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        Injector.init(this)
    }

}
