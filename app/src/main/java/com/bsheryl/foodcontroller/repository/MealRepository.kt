package com.bsheryl.foodcontroller.repository

import androidx.lifecycle.LiveData
import com.bsheryl.foodcontroller.dao.MealDao
import com.bsheryl.foodcontroller.entities.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MealRepository(private val mealDao: MealDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val mealList: LiveData<List<Meal>> = mealDao.getMeals()

    fun addMeal(meal: Meal) {
        coroutineScope.launch(Dispatchers.IO) {
            mealDao.addMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        coroutineScope.launch(Dispatchers.IO) {
            mealDao.deleteMeal(meal)
        }
    }

    fun getMealsByDate(date: String): Flow<List<Meal>> {
        return mealDao.getMealsByDate(date)
    }
}