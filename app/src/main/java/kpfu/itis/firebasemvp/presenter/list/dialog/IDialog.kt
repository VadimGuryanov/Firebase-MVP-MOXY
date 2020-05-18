package kpfu.itis.firebasemvp.presenter.list.dialog

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IDialog : MvpView{

    fun showError(mess: String?)

}
