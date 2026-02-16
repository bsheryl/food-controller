package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
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
    @ColumnInfo(name = "meal_date") var mealDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())),
    @ColumnInfo(name = "meal_time") var mealTime: String = SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis())),
    var weight: Double = 100.0,
    var pro: Double = 0.0,
    var fat: Double = 0.0,
    var carbs: Double = 0.0,
    var cal: Double = 0.0
)