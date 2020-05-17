package kpfu.itis.firebasemvp.presenter.list.data

import io.reactivex.Completable
import io.reactivex.Single
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime

interface AnimeRepository {

    fun getData() : Single<List<Anime>>
    fun saveData(anime: Anime) : Single<Anime>

}
