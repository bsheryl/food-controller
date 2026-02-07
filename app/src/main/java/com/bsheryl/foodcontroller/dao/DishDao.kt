package com.bsheryl.foodcontroller.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bsheryl.foodcontroller.entities.Dish

@Dao
interface DishDao {
    @Insert
    fun addDish(dish: Dish)

    @Query("select * from dishes")
    fun getDishes(): LiveData<List<Dish>>

    @Delete
    fun deleteDish(dish: Dish)
}