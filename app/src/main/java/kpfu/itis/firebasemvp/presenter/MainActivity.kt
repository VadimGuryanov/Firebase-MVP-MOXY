package kpfu.itis.firebasemvp.presenter

import android.os.Bundle
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import moxy.MvpAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container_main, SignInFragment.newInstance())
                commit()
            }
        }
    }

    private val navigator = object : SupportAppNavigator(this, R.id.container_main) {}

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}
