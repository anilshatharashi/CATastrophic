package com.anilsandroidtraining.catastrophic.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anilsandroidtraining.catastrophic.databinding.ViewListItemCatBinding
import com.anilsandroidtraining.catastrophic.presentation.model.UiCat

class CatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val catList = mutableListOf<UiCat?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatItemViewHolder(ViewListItemCatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CatItemViewHolder ->
                catList[position]?.run {
                    holder.bindTo(this)
//                    holder.itemView.setOnClickListener { catItemClickListener.invoke(this) }
                }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (catList[position] == null) TYPE_LOADING else TYPE_CONTENT

    override fun getItemCount(): Int = catList.size

    fun addCatList(list: List<UiCat>) {
        catList.addAll(list)
        notifyDataSetChanged()
    }

    class CatItemViewHolder(private val binding: ViewListItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(uiCat: UiCat) {
            binding.ivViewListItemCat.text = uiCat.id
        }
    }

    companion object {
        private const val TYPE_LOADING = 0
        private const val TYPE_CONTENT = 1
    }
}