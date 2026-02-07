package com.bsheryl.foodcontroller.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bsheryl.foodcontroller.dao.DishDao
import com.bsheryl.foodcontroller.dao.MealDao
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.entities.Meal

@Database(entities = [(Dish::class), Meal::class], version = 1)
abstract class FoodControllerRoomDatabase: RoomDatabase() {
    abstract fun dishDao(): DishDao
    abstract fun mealDao(): MealDao

    companion object {
        private var INSTANCE: FoodControllerRoomDatabase? = null
        fun getInstance(context: Context): FoodControllerRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodControllerRoomDatabase::class.java,
                        "food_controller_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}