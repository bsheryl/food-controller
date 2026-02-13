package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "dish_name") val dishName: String = "",
    val pro: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
    val cal: Int = 0
)