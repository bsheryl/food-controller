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
import java.util.Date

class MealViewModel(application: Application): ViewModel() {
    val mealList: LiveData<List<Meal>>
    private val mealRepository: MealRepository
    var dish by mutableStateOf(Dish())
    var mealDateTime by mutableStateOf(Date())
    var weight by mutableStateOf(0)
    var pro by mutableStateOf(0)
    var fat by mutableStateOf(0)
    var carbs by mutableStateOf(0)
    var cal by mutableStateOf(0)

    init {
        val foodControllerDb = FoodControllerRoomDatabase.getInstance(application)
        val mealDao = foodControllerDb.mealDao()
        mealRepository = MealRepository(mealDao)
        mealList = mealRepository.mealList
    }

    fun changeDish(value: Dish) {
        dish = value
    }

    fun changePro(value: Int) {
        pro = value
    }

    fun changeFat(value: Int) {
        fat = value
    }

    fun changeCarbs(value: Int) {
        carbs = value
    }

    fun changeCal(value: Int) {
        cal = value
    }

    fun addMeal() {
        mealRepository.addMeal(
            meal = Meal(dish = dish, weight = weight)
        )
    }

    fun deleteMeal() {
        mealRepository.deleteMeal(
            meal = Meal(dish = dish, weight = weight)
        )
    }
}