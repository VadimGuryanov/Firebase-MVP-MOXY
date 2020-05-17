package kpfu.itis.firebasemvp.presenter.auth.registration

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_registration.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class RegistrationFragment : MvpAppCompatFragment(), IRegistrationView {

    @Inject
    lateinit var presenterProvider: Provider<RegistrationPresenter>

    private val presenter: RegistrationPresenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusAuthComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_registration, container, false)
        presenter.initRemoteConfig()
        presenter.fetch()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_sign_up_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_sign_up_pass.error = null
                ti_sign_up_pass.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_sign_up_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_sign_up_email.error = null
                ti_sign_up_email.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        initListener()
    }

    override fun showError(error: String) {
        ti_sign_up_email.error = error
    }

    override fun applyRetrievedLengthLimit(length: Int) {
        et_sign_up_pass.filters = arrayOf(InputFilter.LengthFilter(length))
    }

    private fun initListener() {
        btn_registration.setOnClickListener {
            presenter.signUp(et_sign_up_email.text.toString(), et_sign_up_pass.text.toString())
            presenter.fetch()
        }
    }

    companion object {
        fun newInstance(): RegistrationFragment =
            RegistrationFragment()
    }

}
