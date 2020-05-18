package kpfu.itis.firebasemvp.di

import kpfu.itis.firebasemvp.presenter.App
import kpfu.itis.firebasemvp.presenter.auth.di.AuthComponent
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import kpfu.itis.firebasemvp.presenter.list.di.ListComponent

object Injector {

    lateinit var appComponent: AppComponent
    private var authComponent: AuthComponent? = null
    private var listComponent: ListComponent? = null

    fun init(app: App) {
        appComponent = DaggerAppComponent.builder()
            .app(app)
            .build()
    }

    fun plusAuthComponent(): AuthComponent = authComponent
        ?: appComponent
            .plusAuthComponent()
            .build().also {
                authComponent = it
            }

    fun plusAuthComponent(fragment: SignInFragment): AuthComponent = authComponent
        ?: appComponent
            .plusAuthComponent()
            .signInFragment(fragment)
            .build().also {
                authComponent = it
            }

    fun clearAuthComponent() {
        authComponent = null
    }

    fun plusListComponent(): ListComponent = listComponent
        ?: appComponent
            .plusListComponent()
            .build().also {
                listComponent = it
            }

    fun clearListComponent() {
        listComponent = null
    }




}
