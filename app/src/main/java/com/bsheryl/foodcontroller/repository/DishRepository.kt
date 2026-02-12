package com.bsheryl.foodcontroller.repository

import androidx.lifecycle.LiveData
import com.bsheryl.foodcontroller.dao.DishDao
import com.bsheryl.foodcontroller.entities.Dish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DishRepository(private val dishDao: DishDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val dishList: LiveData<List<Dish>> = dishDao.getDishes()

    fun addDish(dish: Dish = Dish()) {
        coroutineScope.launch(Dispatchers.IO) {
            dishDao.addDish(dish)
        }
    }

    fun deleteDish(dish: Dish = Dish()) {
        coroutineScope.launch(Dispatchers.IO) {
            dishDao.deleteDish(dish)
        }
    }
}