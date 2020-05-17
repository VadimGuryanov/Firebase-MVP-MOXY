package kpfu.itis.firebasemvp.presenter.auth.signin

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.circularreveal.CircularRevealHelper
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface ISingInView : MvpView {

    fun showError(mess : String)
    fun signGoogle(intent: Intent)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(mess: String)

}
