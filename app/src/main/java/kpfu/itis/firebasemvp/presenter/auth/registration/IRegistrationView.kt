package kpfu.itis.firebasemvp.presenter.auth.registration

import io.reactivex.Completable
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IRegistrationView : MvpView {

    fun showError(error: String)
    fun applyRetrievedLengthLimit(length: Int)

}
