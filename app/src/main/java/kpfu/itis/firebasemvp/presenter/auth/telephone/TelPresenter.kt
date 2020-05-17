package kpfu.itis.firebasemvp.presenter.auth.telephone

import android.util.Log
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.navigation.Screens
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AuthScope
@InjectViewState
class TelPresenter @Inject constructor(
    private var phoneAuthProvider: PhoneAuthProvider,
    private var repository: AuthRepository,
    private var router: Router
) : MvpPresenter<ITelephoneSignIn>() {

    private var storedVerificationId: String = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var disposable: Disposable? = null

    fun getCode(tel: String) {
        phoneAuthProvider.verifyPhoneNumber(
            tel,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            callback
        )
    }

    fun getRepeat(tel: String) {
        resendVerificationCode(tel, resendToken)
    }

    fun signIn(code: String) {
        var credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        disposable = repository.googleAuth(credential)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { router.newRootScreen(Screens.ListScreen(it.uid))},
                onError = {viewState.showToast(it.message ?: "Phone auth error")}
            )
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            disposable = repository.googleAuth(credential)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { router.newRootScreen(Screens.ListScreen(it.uid))},
                    onError = {
                        Log.e(it::class.java.name, it.message ?: "Phone auth error")
                        viewState.showError(it.message ?: "Phone auth error")
                    }
                )
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e(e::class.java.name, e.message ?: "Phone auth error")
            viewState.showError(e.message ?: "Phone auth error")
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            storedVerificationId = verificationId
            resendToken = token
        }

    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        phoneAuthProvider.verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            callback,
            token
        )
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }

}
