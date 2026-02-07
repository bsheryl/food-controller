package com.bsheryl.foodcontroller.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bsheryl.foodcontroller.databases.FoodControllerRoomDatabase
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.repository.DishRepository

class DishViewModel(application: Application): ViewModel() {
    val dishList: LiveData<List<Dish>>
    private val dishRepository: DishRepository
    var dishName by mutableStateOf("")
    var pro by mutableStateOf(0)
    var fat by mutableStateOf(0)
    var carbs by mutableStateOf(0)
    var cal by mutableStateOf(0)

    init {
        val foodControllerDb = FoodControllerRoomDatabase.getInstance(application)
        val dishDao = foodControllerDb.dishDao()
        dishRepository = DishRepository(dishDao)
        dishList = dishRepository.dishList
    }

    fun changeName(value: String) {
        dishName = value
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

    fun addDish() {
        dishRepository.addDish(
            dish = Dish(
                dishName = dishName,
                pro = pro,
                fat = fat,
                carbs = carbs,
                cal = cal)
        )
    }

    fun deleteDish() {
        dishRepository.deleteDish(
            dish = Dish(
                dishName = dishName,
                pro = pro,
                fat = fat,
                carbs = carbs,
                cal = cal)
        )
    }
}