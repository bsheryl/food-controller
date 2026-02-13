package com.bsheryl.foodcontroller.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bsheryl.foodcontroller.dao.DishDao
import com.bsheryl.foodcontroller.entities.Dish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getById(id: String?): Flow<Dish> = dishDao.getById(id)

//    fun getById(id: String?): Flow<Dish> {
//        var dish: Flow<Dish> = Dish()
//        coroutineScope.launch(Dispatchers.IO) {
//            dish = dishDao.getById(id)
//            Log.d("DishRepository", "dish: " + dish)
//        }
//        Log.d("DishRepository", "before return dish: " + dish)
//        return dish
//    }

//    }
//    suspend fun getById(id: String?): Dish {
//        return withContext(Dispatchers.IO) {
//            val dish: Dish = dishDao.getById(id)
//            Log.d("DishRepository", "dish: $dish")
//            dish // возвращаем результат из блока
//        }
//    }
}