package kpfu.itis.firebasemvp.presenter.list.dialog

import kpfu.itis.firebasemvp.presenter.auth.registration.IRegistrationView
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IDialog : MvpView{

    fun showError(mess: String)

}
