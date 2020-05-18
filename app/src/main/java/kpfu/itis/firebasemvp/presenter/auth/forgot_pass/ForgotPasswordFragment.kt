package kpfu.itis.firebasemvp.presenter.auth.forgot_pass

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ForgotPasswordFragment : MvpAppCompatFragment(), IForgotPassword {

    @Inject
    lateinit var presenterProvider: Provider<ForgotPasswordPresenter>

    private val presenter: ForgotPasswordPresenter by moxyPresenter {
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
    ): View? = inflater.inflate(R.layout.fragment_forgot_pass, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_pass.error = null
                ti_pass.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_email.error = null
                ti_email.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        initListener()
    }

    override fun showError(error: String) {
        ti_email.error = error
        ti_pass.error = error
    }

    override fun visiblePassword() {
        btn_new_pass.visibility = View.VISIBLE
        ti_pass.visibility = View.VISIBLE
        btn_reset.visibility = View.GONE
        ti_email.visibility = View.GONE
    }

    override fun onDestroy() {
        Injector.clearAuthComponent()
        super.onDestroy()
    }

    private fun initListener() {
        btn_reset.setOnClickListener {
            presenter.resetPass(et_email.text.toString())
        }
        btn_new_pass.setOnClickListener {
            presenter.newPass(et_pass.text.toString())
        }
    }

    companion object {
        fun newInstance() : ForgotPasswordFragment = ForgotPasswordFragment()
    }

}
