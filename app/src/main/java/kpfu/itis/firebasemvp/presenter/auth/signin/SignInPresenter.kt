package kpfu.itis.firebasemvp.presenter.auth.signin

import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.navigation.Screens
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

@AuthScope
@InjectViewState
class SignInPresenter @Inject constructor(
    private var repository: AuthRepository,
    private var googleApiClient : GoogleApiClient,
    private var router: Router
) : MvpPresenter<ISingInView>() {

    private var disposable : Disposable? = null

    fun signIn(email: String, password: String) {
        disposable = repository.signInEmail(email, password)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    router.newRootScreen(Screens.ListScreen(it.uid))
                },
                onError = {
                    Log.e("email", it.message ?: "err")
                    viewState.showError(it.message.toString())
                }
            )
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        disposable = repository.googleAuth(credential)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { router.newRootScreen(Screens.ListScreen(it.uid)) },
                onError = {
                    viewState.showError("Authentication failed")
                }
            )
    }

    fun signInGoogle() {
        viewState.signGoogle(Auth.GoogleSignInApi.getSignInIntent(googleApiClient))
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }

    fun navigateTo(screen: Screen) {
        router.navigateTo(screen)
    }

}
