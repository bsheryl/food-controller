package com.bsheryl.foodcontroller.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.NavRoutes
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.entities.Meal
import com.bsheryl.foodcontroller.viewmodel.DishViewModel
import com.bsheryl.foodcontroller.viewmodel.MealViewModel
import java.text.SimpleDateFormat
import java.util.Date



@Composable
fun MealScreen(
    navController: NavController, dishId: String?,
    mealViewModel: MealViewModel, dishViewModel: DishViewModel,
    date: String?
) {
    val dish by dishViewModel.getById(dishId).collectAsState(initial = Dish())
    Log.d("MealScreen", "dish: $dish")
    val currentDish = dish
    if (currentDish == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val meal by mealViewModel.meal.collectAsState()
        meal.dishId = currentDish.id
        MealContent(
            navController = navController,
            currentDish = currentDish,
            meal = meal,
            onMealChanged = { updatedMeal -> mealViewModel.updateMeal(updatedMeal)},
            updateMeal = { mealViewModel.addMeal()},
            date = date ?: SimpleDateFormat("yyyy-MM-dd").format(Date())
        )
    }
}

@Composable
fun MealContent(navController: NavController, currentDish: Dish, meal: Meal,
                onMealChanged: (Meal) -> Unit, updateMeal: () -> Unit, date: String) {
    Log.d("MealContent", "dish: $currentDish")
    val w = meal.weight
    onMealChanged(meal.copy(
        mealDate = date,
        pro = currentDish.pro * w / 100,
        fat = currentDish.fat * w / 100,
        carbs = currentDish.carbs * w / 100,
        cal = currentDish.cal * w / 100
    ))
    var currentMeal = meal
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Трапеза", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton({
                        Log.d("MealContent", "meal: ${meal}")
                        updateMeal()
                        navController.navigate(NavRoutes.Main.route) {
                            popUpTo(NavRoutes.Main.route)
                        }
                    }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Подтвердить"
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(it),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Дата", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = currentMeal.mealDate,
//                    value = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())),
                    modifier = Modifier.padding(10.dp),
                    onValueChange = { value -> onMealChanged(currentMeal.copy(
                        mealDate = value)) }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Время", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis())),
                    modifier = Modifier.padding(10.dp),
                    onValueChange = { value -> onMealChanged(currentMeal.copy(
                        mealTime = value)) }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Продукт", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = currentDish.dishName,
                    modifier = Modifier.padding(10.dp),
                    onValueChange = { }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Вес", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = currentMeal.weight.toString(),
                    modifier = Modifier.padding(10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = { value ->
                        val newWeight = value.toDoubleOrNull() ?: 0.0
                        onMealChanged(currentMeal.copy(
                            weight = newWeight,
                            pro = currentDish.pro * newWeight / 100,
                            fat = currentDish.fat * newWeight / 100,
                            carbs = currentDish.carbs * newWeight / 100,
                            cal = currentDish.cal * newWeight / 100
                        ))
                    }
                )
                Log.d("MealScreen", "meal: $currentMeal")
            }
            PfccRow(text = "Белки", currentMeal.pro)
            PfccRow(text = "Жиры", currentMeal.fat)
            PfccRow(text = "Углеводы", currentMeal.carbs)
            PfccRow(text = "Калории", currentMeal.cal)
            Log.d("MealScreen", "meal: $currentMeal")
            Log.d("MealScreen", "dish: $currentDish")
        }
    }
}

@Composable
fun PfccRow(text: String, value: Double) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = value.toString(), modifier = Modifier.padding(10.dp),
            onValueChange = { }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MealScreenPreview() {
    val navController = rememberNavController()
    var dish = Dish("1", "омлет", 10.0, 10.0, 10.0, 10.0)
    var meal = Meal(weight = 200.0)
    MealContent(navController = navController, currentDish = dish, meal = meal,
        onMealChanged = {}, updateMeal = {},
        date = SimpleDateFormat("yyyy-MM-dd").format(Date()))
}