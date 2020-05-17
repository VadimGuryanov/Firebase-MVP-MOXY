package kpfu.itis.firebasemvp.presenter.list.list.recycler

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime
import kpfu.itis.firebasemvp.presenter.list.list.recycler.AnimeViewHolder.Companion.KEY_NAME
import kpfu.itis.firebasemvp.presenter.list.list.recycler.AnimeViewHolder.Companion.KEY_PHOTO_URL

object AnimeDiffutil : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Anime, newItem: Anime): Any? {
        val diffBundle = Bundle()
        if (oldItem.name != newItem.name) {
            diffBundle.putString(KEY_NAME, newItem.name)
        }
        if (oldItem.photoUrl != newItem.photoUrl) {
            diffBundle.putString(KEY_PHOTO_URL, newItem.photoUrl)
        }
        return if (diffBundle.isEmpty) null else diffBundle
    }

}
