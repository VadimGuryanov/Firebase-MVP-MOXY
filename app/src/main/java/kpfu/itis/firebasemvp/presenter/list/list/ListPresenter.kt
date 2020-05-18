package kpfu.itis.firebasemvp.presenter.list.list

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kpfu.itis.firebasemvp.presenter.list.data.AnimeRepository
import kpfu.itis.firebasemvp.presenter.list.di.AnimeListScope
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@AnimeListScope
@InjectViewState
class ListPresenter @Inject constructor(
    private var repository: AnimeRepository
) : MvpPresenter<IList>() {

    private var disposable: Disposable? = null

    fun getData() {
        disposable = repository.getData()
            .subscribeBy(
                onSuccess = {
                    if  (it.isEmpty()) {
                        viewState.showToast("Empty")
                    } else {
                        viewState.initListAdapter(it)
                    }
                },
                onError = {viewState.showToast(it.message ?: "Error")}
            )
    }

    fun update() {
        disposable = repository.getData()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    viewState.update(it)
                    viewState.refresh()
                },
                onError = {viewState.showToast(it.message ?: "Error")}
            )
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    fun download(image: ImageView, url: String) {
        Glide
            .with(image.context)
            .load(url)
            .centerCrop()
            .into(image)
    }

}
