package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "meals",
    foreignKeys = [ForeignKey(
        entity = Dish::class,
        parentColumns = ["id"],
        childColumns = ["dish_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Meal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "dish_id") var dishId: String = "",
    @ColumnInfo(name = "meal_datetime") var mealDateTime: Long = System.currentTimeMillis(),
    var weight: Int = 100,
    var pro: Int = 0,
    var fat: Int = 0,
    var carbs: Int = 0,
    var cal: Int = 0
)