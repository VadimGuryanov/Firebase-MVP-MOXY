package kpfu.itis.firebasemvp.presenter.auth.telephone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_tel.*
import kpfu.itis.firebasemvp.presenter.list.list.ListFragment
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class TelephoneFragment : MvpAppCompatFragment(), ITelephoneSignIn {

    @Inject
    lateinit var presenterProvider: Provider<TelPresenter>

    private val presenter: TelPresenter by moxyPresenter {
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
    ): View? = inflater.inflate(R.layout.fragment_tel, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_code.error = null
                ti_code.isErrorEnabled = false

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_tel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_tel.error = null
                ti_tel.isErrorEnabled = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        initListener()
    }

    override fun showError(mess: String) {
        ti_tel.error = mess
    }

    override fun showToast(mess: String) {
        Toast.makeText(activity, mess, Toast.LENGTH_SHORT).show()
    }

    override fun signIn() {

    }

    private fun initListener() {
        btn_sing_in_tel.setOnClickListener {
            presenter.signIn(et_code.text.toString())
        }
        btn_get_code.setOnClickListener {
            presenter.getCode(et_tel.text.toString())
        }
        btn_repeat_get.setOnClickListener {
            presenter.getRepeat(et_tel.text.toString())
        }
    }

    companion object {

        fun newInstance(): TelephoneFragment = TelephoneFragment()
    }



}