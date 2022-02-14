package com.test.gittrendingrepo.ui.repo.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.test.gittrendingrepo.R
import com.test.gittrendingrepo.core.AppGlide
import com.test.gittrendingrepo.data.repo.Repo
import com.test.gittrendingrepo.databinding.ItemRepoDataBinding

class RepoListAdapter(
    private val onRepoSelected: (id: Int, name: String, cardView: CardView) -> Unit
) : RecyclerView.Adapter<RepoListAdapter.ItemVH>() {

    private val items = mutableListOf<Repo.ListItem>()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    fun updateList(items: List<Repo.ListItem>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapter.ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return RepoVH(ItemRepoDataBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RepoListAdapter.ItemVH, position: Int) {
        holder.bind(items[position])
    }

    inner class RepoVH(private val binding: ItemRepoDataBinding) : ItemVH(binding.root) {

        init {
            binding.apply {
                cardView.setOnClickListener {
                    val item = items[adapterPosition]
                    onRepoSelected(item.id, item.name, cardView)
                }
                favoriteIv.setOnClickListener {
                    val item = items[adapterPosition]
                    item.isFavorite = !item.isFavorite

                    notifyItemChanged(adapterPosition)
                }
            }
        }

        override fun bind(item: Repo.ListItem) {
            binding.apply {
                data = item
                AppGlide.load(item.avatarUrl, avatarIv)

                val context = root.context
                when (item.isFavorite) {
                    true -> {
                        favoriteIv.setImageDrawable(context.getDrawable(android.R.drawable.star_on))
                        clContainer.setBackgroundColor(context.getColor(R.color.selected))
                    }
                    false -> {
                        favoriteIv.setImageDrawable(context.getDrawable(android.R.drawable.star_off))
                        clContainer.setBackgroundColor(context.getColor(android.R.color.transparent))
                    }
                }
            }
        }
    }

    open inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: Repo.ListItem) {}
    }
}