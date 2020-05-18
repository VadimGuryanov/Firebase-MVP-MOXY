package kpfu.itis.firebasemvp.presenter.auth.forgot_pass

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@AuthScope
@InjectViewState
class ForgotPasswordPresenter @Inject constructor(
    private var repository: AuthRepository
) : MvpPresenter<IForgotPassword>() {

    private var disposable : Disposable? = null

    fun resetPass(email: String) {
        disposable = repository.forget(email)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {viewState.visiblePassword()},
                onError = {viewState.showError(it.message)}
            )
    }

    fun newPass(password: String) {
        disposable = repository.updatePassword(password)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {viewState.navigateTo()},
                onError = {
                    viewState.showError(it.message)
                }
            )
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }

}
