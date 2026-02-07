package com.bsheryl.foodcontroller.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bsheryl.foodcontroller.entities.Meal

@Dao
interface MealDao {
    @Insert
    fun addMeal(meal: Meal)

    @Query("select * from meals")
    fun getMeals(): LiveData<List<Meal>>

    @Delete
    fun deleteMeal(meal: Meal)
}