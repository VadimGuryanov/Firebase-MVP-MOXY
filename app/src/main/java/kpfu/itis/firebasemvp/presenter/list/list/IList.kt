package kpfu.itis.firebasemvp.presenter.list.list

import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import kpfu.itis.firebasemvp.presenter.list.list.recycler.AnimeListAdapter
import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IList : MvpView {

    fun initListAdapter(data: List<Anime>)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(mess: String)

    @StateStrategyType(SkipStrategy::class)
    fun update(newList: List<Anime>)

    fun refresh()
}