package kpfu.itis.firebasemvp.presenter.auth.telephone

import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.presenter.auth.data.AuthRepository
import kpfu.itis.firebasemvp.presenter.auth.di.AuthScope
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AuthScope
@InjectViewState
class TelPresenter @Inject constructor(
    private var phoneAuthProvider: PhoneAuthProvider,
    private var repository: AuthRepository
) : MvpPresenter<ITelephoneSignIn>() {

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var disposable: Disposable? = null

    fun getCode(tel: String) {
        if (tel.isEmpty()) {
            viewState.showToast("You don't write number phone")
            return
        }
        phoneAuthProvider.verifyPhoneNumber(
            tel,
            CONST_TIME,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            callback
        )
    }

    fun getRepeat(tel: String) {
        if (tel.isEmpty()) {
            viewState.showToast("You don't write number phone")
            return
        }
        resendVerificationCode(tel, resendToken)
    }

    fun signIn(code: String) {
        storedVerificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, code)
            disposable = repository.googleAuth(credential)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { viewState.navigateTo(it.uid) },
                    onError = { viewState.showToast(it.message) }
                )
        } ?: viewState.showToast("You don't get code")
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            disposable = repository.googleAuth(credential)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { viewState.navigateTo(it.uid) },
                    onError = {
                        viewState.showError(it.message)
                    }
                )
        }

        override fun onVerificationFailed(e: FirebaseException) {
            viewState.showError(e.message)
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
            CONST_TIME,
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

    companion object {

        private const val CONST_TIME : Long = 60

    }

}
