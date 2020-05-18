package kpfu.itis.firebasemvp.presenter.list.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_anime.view.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.di.Injector
import moxy.MvpAppCompatDialogFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class AnimeDialog : MvpAppCompatDialogFragment(), IDialog {

    @Inject
    lateinit var presenterProvider: Provider<AnimePresenter>

    private val presenter: AnimePresenter by moxyPresenter {
        presenterProvider.get()
    }

    private var dialogView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusListComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_anime, null, false)
        val dialog = AlertDialog.Builder(
            ContextThemeWrapper(
                requireContext(),
                R.style.ThemeOverlay_MaterialComponents
            )
        )
            .setTitle("Anime")
            .setPositiveButton("Ok") { _, _ ->
                presenter.add(dialogView?.et_name?.text.toString(), dialogView?.et_url?.text.toString())
            }
            .setNegativeButton("Cancel") { _, _->
                dismiss()
            }
            .setView(dialogView)
            .create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return dialog
    }

    override fun showError(mess: String) {
        dialogView?.et_name?.error = mess
    }

    override fun onDestroy() {
        Injector.clearListComponent()
        super.onDestroy()
    }

    companion object {

        fun show(fragmentManager: FragmentManager): AnimeDialog =
            AnimeDialog().apply {
                show(fragmentManager, AnimeDialog::class.java.name)
            }
    }

}
