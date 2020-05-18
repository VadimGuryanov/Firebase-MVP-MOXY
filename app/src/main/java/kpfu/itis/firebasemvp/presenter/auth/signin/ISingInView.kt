package kpfu.itis.firebasemvp.presenter.auth.signin

import android.content.Intent
import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ISingInView : MvpView {

    fun showError(mess : String)
    fun signGoogle(intent: Intent)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(mess: String)

}
