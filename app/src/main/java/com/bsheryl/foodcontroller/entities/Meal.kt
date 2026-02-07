package com.bsheryl.foodcontroller.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
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
class Meal {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString()
    @NotNull
    @ColumnInfo(name = "dish_id")
    var dishId: String = ""
    @NotNull
    @ColumnInfo(name = "meal_datetime")
    var mealDateTime: Long = System.currentTimeMillis()
    @NotNull
    @ColumnInfo(name = "weight")
    var weight: Int = 0
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

    constructor()

    constructor(dish: Dish, weight: Int) {
        this.dishId = dish.id
        this.weight = weight
        this.pro = dish.pro * weight / 100
        this.fat = dish.fat * weight / 100
        this.carbs = dish.carbs * weight / 100
        this.cal = dish.cal * weight / 100
    }
}