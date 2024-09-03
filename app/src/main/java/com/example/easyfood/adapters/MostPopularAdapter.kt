package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.pojo.MealByCategory

    class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
        lateinit var onItemClick: ((MealByCategory) -> Unit)
        lateinit var onLongItemClick: ((MealByCategory) -> Unit)
        private var mealsList = ArrayList<MealByCategory>()

        fun setMeals(mealsList: ArrayList<MealByCategory>) {
            this.mealsList = mealsList
            notifyDataSetChanged()

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
            return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun getItemCount(): Int {
            return mealsList.size
        }

        override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
            Glide.with(holder.itemView)
                .load(mealsList[position].strMealThumb)
                .into(holder.binding.imgPopularMealItem)
            holder.itemView.setOnClickListener {
                onItemClick.invoke(mealsList[position])
            }
            holder.itemView.setOnLongClickListener {
                onLongItemClick.invoke(mealsList[position])
                true
            }
        }

        class PopularMealViewHolder( val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)
    }