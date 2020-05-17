package kpfu.itis.firebasemvp.di

import dagger.BindsInstance
import dagger.Component
import kpfu.itis.firebasemvp.di.modules.AppModule
import kpfu.itis.firebasemvp.di.modules.FirebaseModule
import kpfu.itis.firebasemvp.di.modules.NavigationModule
import kpfu.itis.firebasemvp.presenter.App
import kpfu.itis.firebasemvp.presenter.MainActivity
import kpfu.itis.firebasemvp.presenter.auth.di.AuthComponent
import kpfu.itis.firebasemvp.presenter.list.di.ListComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class, NavigationModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: App) : Builder
        fun build() : AppComponent
    }

    fun plusAuthComponent() : AuthComponent.Builder
    fun plusListComponent() : ListComponent.Builder
    fun inject(mainActivity: MainActivity)
}
