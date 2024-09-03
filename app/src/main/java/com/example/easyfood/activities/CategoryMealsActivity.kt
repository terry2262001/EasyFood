package com.example.easyfood.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapters.CategoryMealsAdapter
import com.example.easyfood.databinding.ActivityCategoryMealsBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.videoModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORYNAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealList ->
            if (mealList != null) {
                binding.tvCategoryCount.text = mealList.size.toString()
                categoryMealsAdapter.setMealsList(mealList)
            }

        })

    }

        private fun prepareRecyclerView() {
            categoryMealsAdapter = CategoryMealsAdapter()
            binding.rvMeals.apply {
                layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL, false)
                adapter = categoryMealsAdapter
            }
        }
}