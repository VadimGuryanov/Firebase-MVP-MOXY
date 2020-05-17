package kpfu.itis.firebasemvp.presenter.auth.registration

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.navigation.Screens
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@AuthScope
@InjectViewState
class RegistrationPresenter @Inject constructor(
    private var repository: AuthRepository,
    private var router: Router
) : MvpPresenter<IRegistrationView>() {

    private var disposable : Disposable? = null

    fun signUp(email: String, password: String) {
        disposable = repository.signUpEmail(email, password)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onComplete = {router.newRootScreen(Screens.SignInScreen)},
                onError = {viewState.showError(it.message.toString())}
            )
    }

    fun fetch() {
        disposable = repository.fetch()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {viewState.applyRetrievedLengthLimit(getMsgLength().toInt()) },
                onError = {
                    viewState.showError(it.message ?: "Error fetch")
                    viewState.applyRetrievedLengthLimit(getMsgLength().toInt())
                }
            )
    }

    fun getMsgLength() : Long = repository.getMsgLength()

    fun initRemoteConfig() {
        repository.initRemoteConfig()
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }
}
