package com.bsheryl.foodcontroller.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bsheryl.foodcontroller.databases.FoodControllerRoomDatabase
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.entities.Meal
import com.bsheryl.foodcontroller.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class MealViewModel(application: Application): ViewModel() {
    val mealList: LiveData<List<Meal>>
    private val mealRepository: MealRepository
    var dish by mutableStateOf(Dish())
    private val _meal = MutableStateFlow<Meal>(Meal())
    val meal: StateFlow<Meal> = _meal.asStateFlow() as StateFlow<Meal>

    init {
        val foodControllerDb = FoodControllerRoomDatabase.getInstance(application)
        val mealDao = foodControllerDb.mealDao()
        mealRepository = MealRepository(mealDao)
        mealList = mealRepository.mealList
    }

    fun setupInitialMeal(dishId: String?) {
        if (_meal.value.dishId.isEmpty()) {
            _meal.value = Meal(dishId = dishId ?: "")
        }
    }

    fun addMeal() {
        mealRepository.addMeal(meal = meal.value)
    }

    fun updateMeal(newMeal: Meal) {
        _meal.value = newMeal
    }

    fun deleteMeal() {
        mealRepository.deleteMeal(meal = meal.value)
    }
}