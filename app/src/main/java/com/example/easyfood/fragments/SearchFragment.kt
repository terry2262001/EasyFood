package com.example.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.adapters.MealsAdapter
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.videoModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.imgSearchArrow.setOnClickListener {
            searchMeals()
        }
        observeSearchMealsLiveData()
        var searchJob: Job? = null
        binding.etSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }

    }

    private fun observeSearchMealsLiveData() {
        viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner) { mealsList ->
            searchRecyclerViewAdapter.differ.submitList(mealsList)
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.etSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.rvSearchMeals.apply {
            layoutManager =  GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter
        }
    }
}