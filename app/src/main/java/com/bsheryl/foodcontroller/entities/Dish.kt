package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "dish_name") var dishName: String = "",
    var pro: Double = 0.0,
    var fat: Double = 0.0,
    var carbs: Double = 0.0,
    var cal: Double = 0.0
)