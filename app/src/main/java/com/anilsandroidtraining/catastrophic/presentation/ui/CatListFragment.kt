package com.anilsandroidtraining.catastrophic.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anilsandroidtraining.catastrophic.databinding.FragmentCatListBinding
import com.anilsandroidtraining.catastrophic.presentation.model.UiCat
import com.anilsandroidtraining.catastrophic.presentation.model.UiCatModel

class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catListAdapter: CatListAdapter

    //TODO remove this once the real data is is in place fake data
    private val uiCatModel: UiCatModel = UiCatModel()

    init {
        //TODO remove this once the real data is is in place fake data
        uiCatModel.cats = listOf(
            UiCat("1", "blabla", 480, 480),
            UiCat("2", "blabla2", 480, 480),
            UiCat("4234", "blabla2", 480, 480),
            UiCat("22342", "blabla2", 480, 480),
            UiCat("242", "blabla2", 480, 480),
            UiCat("3", "blabla3", 480, 480)
        )

    }

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
        catListAdapter.addCatList(uiCatModel.cats)
        binding.recyclerViewCatList.adapter = catListAdapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GRID_LAYOUT_SPAN_COUNT = 3

    }
}