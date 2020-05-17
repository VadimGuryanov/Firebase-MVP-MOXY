package kpfu.itis.firebasemvp.presenter.list.list.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_anime.view.*
import kpfu.itis.firebasemvp.R
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime

class AnimeViewHolder(
    override val containerView: View,
    private var download: (ImageView, String) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {

        const val KEY_NAME = "name"
        const val KEY_PHOTO_URL = "photoUrl"

        fun create(parent: ViewGroup, download: (ImageView, String) -> Unit) =
            AnimeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false),
                download
            )
    }

    fun bind(anime: Anime) {
        containerView.apply {
            tv_anime.text = anime.name
            download(iv_anime, anime.photoUrl)
        }
    }

    fun updateFromBundle(bundle: Bundle?) {
        bundle?.apply {
           bind(
               Anime(
                   getString(KEY_NAME) ?: "empty",
                   getString(KEY_PHOTO_URL) ?: ""
               )
           )
        }
    }


}