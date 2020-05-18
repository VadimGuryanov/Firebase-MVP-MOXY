package kpfu.itis.firebasemvp.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kpfu.itis.firebasemvp.presenter.auth.forgot_pass.ForgotPasswordFragment
import kpfu.itis.firebasemvp.presenter.auth.registration.RegistrationFragment
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import kpfu.itis.firebasemvp.presenter.auth.telephone.TelephoneFragment
import kpfu.itis.firebasemvp.presenter.list.dialog.AnimeDialog
import kpfu.itis.firebasemvp.presenter.list.list.ListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SignInScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? = SignInFragment.newInstance()
    }

    object RegistrationScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? = RegistrationFragment.newInstance()
    }

    object TelephoneScreen : SupportAppScreen() {
        override fun getFragment() : Fragment? = TelephoneFragment.newInstance()
    }

    object ForgotPassScreen : SupportAppScreen() {
        override fun getFragment() : Fragment? = ForgotPasswordFragment.newInstance()
    }

    data class DialogScreen(var fragmentManager: FragmentManager) : SupportAppScreen() {
        override fun getFragment(): Fragment? = AnimeDialog.show(fragmentManager)
    }

    data class ListScreen(var id: String) : SupportAppScreen() {
        override fun getFragment(): Fragment? = ListFragment.newInstance(id)
    }

}
