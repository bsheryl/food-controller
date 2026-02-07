package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

@Entity(tableName = "dishes")
class Dish {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString()
    @NotNull
    @ColumnInfo(name = "dish_name")
    var dishName: String = ""
    @NotNull
    @ColumnInfo(name = "pro")
    var pro: Int = 0
    @NotNull
    @ColumnInfo(name = "fat")
    var fat: Int = 0
    @NotNull
    @ColumnInfo(name = "carbs")
    var carbs: Int = 0
    @NotNull
    @ColumnInfo(name = "cal")
    var cal: Int = 0

    constructor() {}

    constructor(dishName: String, pro: Int, fat: Int, carbs: Int, cal: Int) {
        this.dishName = dishName
        this.pro = pro
        this.fat = fat
        this.carbs = carbs
        this.cal = cal
    }
}