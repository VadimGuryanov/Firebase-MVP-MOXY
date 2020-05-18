package kpfu.itis.firebasemvp.presenter.list.dialog

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.presenter.list.data.AnimeRepository
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import kpfu.itis.firebasemvp.presenter.list.di.AnimeListScope
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@AnimeListScope
@InjectViewState
class AnimePresenter @Inject constructor(
    private var repository: AnimeRepository
) : MvpPresenter<IDialog>() {

    private var disposable : Disposable? = null

    fun add(name: String, url : String) {
        disposable = repository.saveData(Anime(name, url))
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {},
                onError = {viewState.showError(it.message)}
            )
    }

    override fun onDestroy() {
        if (disposable?.isDisposed == true) {
            disposable?.dispose()
        }
    }

}
