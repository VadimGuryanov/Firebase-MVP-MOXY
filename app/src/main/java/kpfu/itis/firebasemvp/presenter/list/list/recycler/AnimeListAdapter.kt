package kpfu.itis.firebasemvp.presenter.list.list.recycler

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import kpfu.itis.firebasemvp.presenter.list.data.model.Anime

class AnimeListAdapter (
    private var dataSource: List<Anime>,
    private var download: (ImageView, String) -> Unit
) : ListAdapter<Anime, AnimeViewHolder>(AnimeDiffutil)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder =
        AnimeViewHolder.create(parent, download)

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) =
        holder.bind(dataSource[position])

    override fun onBindViewHolder(
        holder: AnimeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val bundle = payloads[0] as? Bundle
            holder.updateFromBundle(bundle)
        }
    }

    fun update(newList: List<Anime>) {
        dataSource = newList
        submitList(dataSource)
        notifyDataSetChanged()
    }

}
