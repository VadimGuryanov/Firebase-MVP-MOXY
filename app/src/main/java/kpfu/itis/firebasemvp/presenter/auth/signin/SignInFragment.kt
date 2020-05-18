package kpfu.itis.firebasemvp.presenter.auth.signin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_signin.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import kpfu.itis.firebasemvp.navigation.Screens
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SignInFragment : MvpAppCompatFragment(), ISingInView, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var presenterProvider: Provider<SignInPresenter>

    private val presenter: SignInPresenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var firebaseAnalytics : FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusAuthComponent(this).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_signin, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_sign_in_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_sign_in_pass.error = null
                ti_sign_in_pass.isErrorEnabled = false

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_sign_in_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_sign_in_email.error = null
                ti_sign_in_email.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        initListener()
    }

    override fun showError(mess: String) {
        ti_sign_in_email.error = mess
    }

    override fun signGoogle(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun showToast(mess: String) {
        Toast.makeText(activity, mess, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)?.let {
                    presenter.firebaseAuthWithGoogle(it)
                } ?: run {
                    Toast.makeText(activity, "Sign in error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Toast.makeText(activity, e.message ?: "Sing in error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListener() {
        btn_signin.setOnClickListener {
            presenter.signIn(et_sign_in_email.text.toString(), et_sign_in_pass.text.toString())
        }
        tv_registration.setOnClickListener {
            presenter.navigateTo(Screens.RegistrationScreen)
        }
        btn_google.setOnClickListener { presenter.signInGoogle() }
        btn_tel_auth.setOnClickListener { presenter.navigateTo(Screens.TelephoneScreen) }
        tv_forgot.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.VALUE, et_sign_in_email.text.toString())
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
            presenter.navigateTo(Screens.ForgotPassScreen)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        showToast("Google Play Services error.")
    }

    override fun onDestroy() {
        Injector.clearAuthComponent()
        super.onDestroy()
    }

    companion object {

        private const val RC_SIGN_IN = 9001

        fun newInstance(): SignInFragment = SignInFragment()
    }
}
