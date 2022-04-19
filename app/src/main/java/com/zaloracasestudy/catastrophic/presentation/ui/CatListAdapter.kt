package com.zaloracasestudy.catastrophic.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaloracasestudy.catastrophic.databinding.ViewItemLoadingBinding
import com.zaloracasestudy.catastrophic.databinding.ViewListItemCatBinding
import com.zaloracasestudy.catastrophic.presentation.model.UiCat

class CatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val catList = mutableListOf<UiCat?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> LoadingViewHolder(ViewItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
            else -> CatItemViewHolder(ViewListItemCatBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        }
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

    fun addProgressBar() {
        catList.add(null)
        notifyItemInserted(catList.size - 1)
    }

    fun removeProgressBar() {
        catList.apply {
            if (size != 0) {
                removeAt(size - 1)
                notifyItemRemoved(size)
            }
        }
    }

    override fun getItemCount(): Int = catList.size

    fun addCatList(list: List<UiCat>) {
        catList.addAll(list)
        notifyDataSetChanged()
    }

    class CatItemViewHolder(private val binding: ViewListItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

       fun bindTo(uiCat: UiCat) {
            uiCat.url?.let { binding.ivViewListItemCat.loadFromUrl(it) }
        }
    }

    class LoadingViewHolder(binding: ViewItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.progressBar
        }
    }

    companion object {
        private const val TYPE_LOADING = 0
        private const val TYPE_CONTENT = 1
    }
}