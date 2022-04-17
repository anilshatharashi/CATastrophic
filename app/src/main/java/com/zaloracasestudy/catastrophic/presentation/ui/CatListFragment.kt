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
import com.zaloracasestudy.catastrophic.presentation.CatListState.Success
import com.zaloracasestudy.catastrophic.presentation.CatsViewModel
import com.zaloracasestudy.catastrophic.presentation.model.UiCat

class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catListAdapter: CatListAdapter

    private val viewModel: CatsViewModel by activityViewModels()

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

        binding.recyclerViewCatList.adapter = catListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.catListState.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> showContentView(it.catList)
                else -> {
                    //Do nothing
                }
            }
        }
    }

    private fun showContentView(list: List<UiCat>?) {
        catListAdapter.addCatList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GRID_LAYOUT_SPAN_COUNT = 3

    }
}