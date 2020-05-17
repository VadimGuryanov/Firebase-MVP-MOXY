package kpfu.itis.firebasemvp.presenter.list.di

import dagger.Subcomponent
import kpfu.itis.firebasemvp.presenter.list.dialog.AnimeDialog
import kpfu.itis.firebasemvp.presenter.list.list.ListFragment

@AnimeListScope
@Subcomponent(modules = [ListModule::class])
interface ListComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : ListComponent
    }

    fun inject(listFragment: ListFragment)
    fun inject(animeDialog: AnimeDialog)

}
