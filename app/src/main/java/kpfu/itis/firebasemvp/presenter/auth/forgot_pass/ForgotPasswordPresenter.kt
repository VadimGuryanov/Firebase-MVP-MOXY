package kpfu.itis.firebasemvp.presenter.auth.forgot_pass

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toMaybe
import kpfu.itis.firebasemvp.navigation.Screens
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@AuthScope
@InjectViewState
class ForgotPasswordPresenter @Inject constructor(
    private var repository: AuthRepository,
    private var router: Router
) : MvpPresenter<IForgotPassword>() {

    private var disposable : Disposable? = null

    fun resetPass(email: String) {
        disposable = repository.forget(email)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {viewState.visiblePassword()},
                onError = {viewState.showError(it.message ?: "Error")}
            )
    }

    fun newPass(password: String) {
        disposable = repository.updatePassword(password)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {router.newRootScreen(Screens.SignInScreen)},
                onError = {
                    Log.e("new pass", it.message ?: "err")
                    viewState.showError(it.message ?: "Error")
                }
            )
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }

}