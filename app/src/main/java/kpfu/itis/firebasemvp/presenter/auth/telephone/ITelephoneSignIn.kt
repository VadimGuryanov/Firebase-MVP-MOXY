package kpfu.itis.firebasemvp.presenter.auth.telephone

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ITelephoneSignIn : MvpView {

    fun navigateTo(id : String)
    fun showError(mess : String?)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(mess: String?)
}
