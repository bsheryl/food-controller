package com.bsheryl.foodcontroller.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bsheryl.foodcontroller.databases.FoodControllerRoomDatabase
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.entities.Meal
import com.bsheryl.foodcontroller.repository.MealRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import java.text.SimpleDateFormat
import java.util.Date

class MealViewModel(application: Application): ViewModel() {
    val mealList: LiveData<List<Meal>>
    private val mealRepository: MealRepository
    var dish by mutableStateOf(Dish())
    private val _meal = MutableStateFlow(Meal())
    val meal: StateFlow<Meal> = _meal.asStateFlow() as StateFlow<Meal>

    private val _selectedDate = MutableStateFlow(SimpleDateFormat("yyyy-MM-dd").format(Date()))
    val selectedDate: StateFlow<String> = _selectedDate
    @OptIn(ExperimentalCoroutinesApi::class)
    val mealsByDate = selectedDate.flatMapLatest { date ->
        mealRepository.getMealsByDate(date)
    }.asLiveData()

    fun setDate(newDate: String) {
        _selectedDate.value = newDate
    }

    init {
        val foodControllerDb = FoodControllerRoomDatabase.getInstance(application)
        val mealDao = foodControllerDb.mealDao()
        mealRepository = MealRepository(mealDao)
        mealList = mealRepository.mealList
    }

//    fun setupInitialMeal(dishId: String?) {
//        if (_meal.value.dishId.isEmpty()) {
//            _meal.value = Meal(dishId = dishId ?: "")
//        }
//    }

    fun addMeal() {
        mealRepository.addMeal(meal = meal.value)
    }

    fun updateMeal(newMeal: Meal) {
        _meal.value = newMeal
    }

    fun deleteMeal(meal: Meal) {
        mealRepository.deleteMeal(meal = meal)
    }

    fun getMealsByDate(date: String): Flow<List<Meal>> {
        return mealRepository.getMealsByDate(date)
    }
}