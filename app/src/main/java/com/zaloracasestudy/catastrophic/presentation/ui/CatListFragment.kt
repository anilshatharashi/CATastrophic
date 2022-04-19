package com.zaloracasestudy.catastrophic.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaloracasestudy.catastrophic.databinding.FragmentCatListBinding
import com.zaloracasestudy.catastrophic.presentation.CatListState.Loading
import com.zaloracasestudy.catastrophic.presentation.CatListState.Success
import com.zaloracasestudy.catastrophic.presentation.CatsViewModel
import com.zaloracasestudy.catastrophic.presentation.model.UiCat
import com.zaloracasestudy.catastrophic.presentation.ui.RecyclerViewPaginationListener.Companion.PAGE_START

class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catListAdapter: CatListAdapter

    private val viewModel: CatsViewModel by activityViewModels()
    private var pageIndex: Int = PAGE_START

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        val gridLayoutManager = GridLayoutManager(
            activity,
            GRID_LAYOUT_SPAN_COUNT,
            RecyclerView.VERTICAL,
            false
        )

        binding.recyclerViewCatList.layoutManager = gridLayoutManager
        catListAdapter = CatListAdapter()

        binding.recyclerViewCatList.addOnScrollListener(object :
            RecyclerViewPaginationListener(gridLayoutManager) {

            override val isLastPage: Boolean
                get() = viewModel.isLastPage.value?:false

            override val isLoading: Boolean
                get() = viewModel.isNextPageLoading.value?:false

            override fun loadMoreItems() {
                viewModel.pageIndex.value = pageIndex++
                viewModel.fetchCatList()
            }
        })

        binding.recyclerViewCatList.adapter = catListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.catListState.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> showLoadingView()
                is Success -> showContentView(it.catList)
                else -> {
                    //Do nothing
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) catListAdapter.addProgressBar()
        }
    }

    private fun showLoadingView() {
        binding.run {
            progressBar.visibility = View.VISIBLE
            recyclerViewCatList.visibility = View.GONE
        }
    }

    private fun showContentView(catList: List<UiCat>) {
        if (pageIndex != PAGE_START) catListAdapter.removeProgressBar()
        catListAdapter.addCatList(catList)

        hideLoadingView()
    }

    private fun hideLoadingView() {
        binding.run {
            progressBar.visibility = View.GONE
            recyclerViewCatList.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GRID_LAYOUT_SPAN_COUNT = 3

    }
}