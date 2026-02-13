package com.bsheryl.foodcontroller.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun MealScreen(navController: NavController, dishId: String?,
               mealViewModel: MealViewModel, dishViewModel: DishViewModel
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
            updateMeal = { mealViewModel.addMeal()}
        )
    }
}

@Composable
fun MealContent(navController: NavController, currentDish: Dish, meal: Meal,
                onMealChanged: (Meal) -> Unit, updateMeal: () -> Unit) {
    Log.d("MealContent", "dish: $currentDish")
    val currentMeal = meal
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
                Text(text = "Время", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis())),
                    modifier = Modifier.padding(10.dp),
                    onValueChange = { value -> onMealChanged(currentMeal.copy(
                        mealDateTime = value.toLongOrNull() ?: System.currentTimeMillis())) }
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
                    onValueChange = { value -> onMealChanged(currentMeal.copy(weight = value.toIntOrNull() ?: 0)) }
                )
                Log.d("MealScreen", "meal: $currentMeal")
            }
            PfccRow(text = "Белки", (currentMeal.copy(pro = currentDish.pro * currentMeal.weight / 100).pro))
            PfccRow(text = "Жиры", (currentMeal.copy(fat = currentDish.fat * currentMeal.weight / 100).fat))
            PfccRow(text = "Углеводы", (currentMeal.copy(carbs = currentDish.carbs * currentMeal.weight / 100).carbs))
            PfccRow(text = "Калории", (currentMeal.copy(cal = currentDish.cal * currentMeal.weight / 100).cal))
            Log.d("MealScreen", "meal: $currentMeal")
            Log.d("MealScreen", "dish: $currentDish")
        }
    }
}

@Composable
fun PfccRow(text: String, value: Int) {
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
    var dish = Dish("1", "омлет", 10, 10, 10, 10)
    var meal = Meal(weight = 200)
    MealContent(navController = navController, currentDish = dish, meal = meal,
        onMealChanged = {}, updateMeal = {})
}