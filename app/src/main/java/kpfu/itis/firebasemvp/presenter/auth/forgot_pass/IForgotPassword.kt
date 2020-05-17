package kpfu.itis.firebasemvp.presenter.auth.forgot_pass

import androidx.fragment.app.Fragment
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IForgotPassword : MvpView {

    fun showError(error: String)
    fun visiblePassword()

}
