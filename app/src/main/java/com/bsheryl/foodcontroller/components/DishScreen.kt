package com.bsheryl.foodcontroller.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.bsheryl.foodcontroller.viewmodel.DishViewModel

@Composable
fun DishScreen(navController: NavController, dishViewModel: DishViewModel) {
    val dish by dishViewModel.selectedDish.collectAsState()
    DishContent(navController = navController, dish = dish,
        onDishChanged = { updatedDish -> dishViewModel.updateDish(updatedDish)},
        updateDish = { dishViewModel.addDish()}
        )
}

@Composable
fun DishContent(navController: NavController, dish: Dish?,
                            onDishChanged: (Dish) -> Unit, updateDish: () -> Unit) {
    val currentDish = dish ?: Dish()
    val numbericKeyboard = KeyboardOptions(keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done)
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
            DishFieldNumberRow(
                field = if (currentDish.pro == 0.0) "" else "${currentDish.pro}",
                onValueChange = { onDishChanged(currentDish.copy(pro = it)) },
                modifier = Modifier.fillMaxWidth(),
                text = "Белки",
                keyboardOptions = numbericKeyboard
            )
            DishFieldNumberRow(
                field = if (currentDish.fat == 0.0) "" else "${currentDish.fat}",
                onValueChange = { onDishChanged(currentDish.copy(fat = it))},
                modifier = Modifier.fillMaxWidth(),
                text = "Жиры",
                keyboardOptions = numbericKeyboard
            )
            DishFieldNumberRow(
                field = if (currentDish.carbs == 0.0) "" else "${currentDish.carbs}",
                onValueChange = { onDishChanged(currentDish.copy(carbs = it)) },
                modifier = Modifier.fillMaxWidth(),
                text = "Углеводы",
                keyboardOptions = numbericKeyboard
            )
            DishFieldNumberRow(
                field = if (currentDish.cal == 0.0) "" else "${currentDish.cal}",
                onValueChange = { onDishChanged(currentDish.copy(cal = it)) },
                modifier = Modifier.fillMaxWidth(),
                text = "Калории",
                keyboardOptions = numbericKeyboard
            )
        }
    }
}

@Composable
fun DishFieldRow(field: String, onValueChange: (String) -> Unit, modifier: Modifier,
                 text: String, keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = field,
            modifier = Modifier.padding(10.dp),
            keyboardOptions = keyboardOptions,
            label = { Text(text = text) }, onValueChange = { value -> onValueChange(value) }
        )
    }
}

@Composable
fun DishFieldNumberRow(
    field: String, // Сюда передаем значение из Dish.toString()
    onValueChange: (Double) -> Unit,
    modifier: Modifier,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    // Локальный стейт, который хранит именно то, что напечатал пользователь
    // remember(field) сработает только если мы переключимся на другой продукт
    var textState by remember(field) {
        mutableStateOf(if (field == "0.0") "" else field)
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = textState,
            modifier = Modifier.padding(10.dp),
            label = { Text(text = text) },
            keyboardOptions = keyboardOptions,
            onValueChange = { newValue ->
                // Разрешаем только цифры и одну точку/запятую
                val filtered = newValue.replace(',', '.')
                    .filterIndexed { index, char ->
                        char.isDigit() || (char == '.' && newValue.indexOf('.') == index)
                    }

                // Обновляем визуальный текст (здесь точка или удаленный .0 не вернутся сами)
                textState = filtered

                // Отправляем число "наверх" в объект Dish
                val doubleValue = filtered.toDoubleOrNull() ?: 0.0
                onValueChange(doubleValue)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DishScreenPreview() {
    val navController = rememberNavController()
    var dish = Dish()
    DishContent(navController = navController, dish = dish,
        onDishChanged = {}, updateDish = {})
}