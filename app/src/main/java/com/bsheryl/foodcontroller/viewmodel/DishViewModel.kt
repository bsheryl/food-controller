package com.bsheryl.foodcontroller.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bsheryl.foodcontroller.databases.FoodControllerRoomDatabase
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.repository.DishRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DishViewModel(application: Application): ViewModel() {
    val dishList: LiveData<List<Dish>>
    private val dishRepository: DishRepository
    private val _selectedDish = MutableStateFlow<Dish?>(null)
    val selectedDish: StateFlow<Dish> = _selectedDish.asStateFlow() as StateFlow<Dish>

    init {
        val foodControllerDb = FoodControllerRoomDatabase.getInstance(application)
        val dishDao = foodControllerDb.dishDao()
        dishRepository = DishRepository(dishDao)
        dishList = dishRepository.dishList
    }

    fun selectedDish(dish: Dish) {
        _selectedDish.value = dish
    }

    fun addDish() {
        dishRepository.addDish(
            dish = selectedDish.value
        )
    }

    fun updateDish(newDish: Dish) {
        _selectedDish.value = newDish
    }

    fun deleteDish() {
        dishRepository.deleteDish(
            dish = selectedDish.value
        )
    }
}