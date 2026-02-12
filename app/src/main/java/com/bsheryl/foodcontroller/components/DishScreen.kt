package com.bsheryl.foodcontroller.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.NavRoutes
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.viewmodel.DishViewModel

@Composable
fun DishScreen(navController: NavController, dishViewModel: DishViewModel) {
    val dish by dishViewModel.selectedDish .collectAsState()
    DishContent(navController = navController, dish = dish,
        onDishChanged = { updatedDish -> dishViewModel.updateDish(updatedDish)},
        updateDish = { dishViewModel.addDish()}
        )
}

@Composable
fun DishContent(navController: NavController, dish: Dish?,
                            onDishChanged: (Dish) -> Unit, updateDish: () -> Unit) {
    val currentDish = dish ?: Dish()
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Продукт", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    if (currentDish.dishName != "") {
                        IconButton({
                            updateDish()
                            navController.navigate(NavRoutes.Dishes.route)
                        }) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Подтвердить"
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            DishFieldRow(field = currentDish.dishName,
                onValueChange = { onDishChanged(currentDish.copy(dishName = it))},
                modifier = Modifier.fillMaxWidth().padding(it),
                text = "Название"
            )
            DishFieldRow(field = currentDish.pro.toString(),
                onValueChange = { onDishChanged(currentDish.copy(pro = it.toInt())) },
                modifier = Modifier.fillMaxWidth(),
                text = "Белки"
            )
            DishFieldRow(field = currentDish.fat.toString(),
                onValueChange = { onDishChanged(currentDish.copy(fat = it.toInt()))},
                modifier = Modifier.fillMaxWidth(),
                text = "Жиры"
            )
            DishFieldRow(field = currentDish.carbs.toString(),
                onValueChange = { onDishChanged(currentDish.copy(carbs = it.toInt())) },
                modifier = Modifier.fillMaxWidth(),
                text = "Углеводы"
            )
            DishFieldRow(field = currentDish.cal.toString(),
                onValueChange = { onDishChanged(currentDish.copy(cal = it.toInt())) },
                modifier = Modifier.fillMaxWidth(),
                text = "Калории"
            )
        }
    }
}

@Composable
fun DishFieldRow(field: String, onValueChange: (String) -> Unit, modifier: Modifier, text: String) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = field, modifier = Modifier.padding(10.dp),
            label = { Text(text = text) }, onValueChange = { value -> onValueChange(value) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateOrEditDishScreenPreview() {
    val navController = rememberNavController()
    var dish = Dish()
    DishContent(navController = navController, dish = dish,
        onDishChanged = {}, updateDish = {})
}