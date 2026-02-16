package com.bsheryl.foodcontroller.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.NavRoutes
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.entities.Meal
import com.bsheryl.foodcontroller.viewmodel.DishViewModel
import com.bsheryl.foodcontroller.viewmodel.MealViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

val formatter = SimpleDateFormat("yyyy-MM-dd")

@Composable
fun MainScreen(navController: NavController, mealViewModel: MealViewModel, dishViewModel: DishViewModel) {
    val meals by mealViewModel.mealsByDate.observeAsState(emptyList())
    val dishes by dishViewModel.dishList.observeAsState(emptyList())
    val date by mealViewModel.selectedDate.collectAsStateWithLifecycle()
    MainContent(navController = navController, meals = meals, dishes = dishes,
        date = date, onDateChange = { newDate -> mealViewModel.setDate(newDate = newDate)},
        onDelete = {meal -> mealViewModel.deleteMeal(meal)})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(navController: NavController, meals: List<Meal>, dishes: List<Dish>,
                date: String, onDateChange: (String) -> Unit,
                onDelete: (Meal) -> Unit) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("FoodController", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Меню"
                        )
                    }
                },
                actions = {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Профиль"
                        )
                    }
                }
            )
        }
    ) {
        Column {
            DateRow(modifier = Modifier.fillMaxWidth().padding(it),
                date = date, onDateChange = onDateChange)
            PfccBox(meals = meals)
//            Text(text = "Здесь будут трапезы", fontSize = 28.sp)
            MealList(meals = meals, navController = navController, dishes = dishes,
                onDelete = onDelete)
            // Кнопка добавить прием пищи
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    onClick = {
                        navController.navigate(NavRoutes.Dishes.route + "/${date}")
//                        navController.navigate("mealScreen/${dish.id}/${date}")
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавление приема пищи",
                    )
                }
            }
        }
    }

}

@Composable
fun PfccBox(meals: List<Meal>) {
    //БЖУК
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column() {
            Row(modifier = Modifier.fillMaxWidth()) {
                PfccElementBox(label = "Белки", sum = meals.sumOf { it.pro })
                PfccElementBox(label = "Жиры", sum = meals.sumOf { it.fat })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                PfccElementBox(label = "Углеводы", sum = meals.sumOf { it.carbs })
                PfccElementBox(label = "Калории", sum = meals.sumOf { it.cal })
            }
        }
    }
}

@Composable
fun RowScope.PfccElementBox(label: String, sum: Double) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(10.dp)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart),
            fontSize = 15.sp,
            text = label
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd),
            fontSize = 15.sp,
            text = "${sum}/0"
        )
    }
}

@Composable
fun MealList(meals: List<Meal>, navController: NavController, dishes: List<Dish>,
             onDelete: (Meal) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(meals) { meal ->
                MealRow(meal, navController = navController,
                    dishes.first { it.id == meal.dishId },
                    onDelete = onDelete)
            }
        }
    }
}

@Composable
fun MealRow(meal: Meal, navController: NavController, dish: Dish,
            onDelete: (Meal) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(dish.dishName, fontSize = 20.sp)
            Text("${meal.mealDate} ${meal.mealTime}", fontSize = 14.sp)
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Text("Б: ${meal.pro}", Modifier.weight(0.2f), fontSize = 14.sp)
                Text("Ж: ${meal.fat}", Modifier.weight(0.2f), fontSize = 14.sp)
                Text("У: ${meal.carbs}", Modifier.weight(0.2f), fontSize = 14.sp)
                Text("К: ${meal.cal}", Modifier.weight(0.2f), fontSize = 14.sp)
            }
        }
        IconButton(onClick = {onDelete(meal)}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить",
                tint = Color.Red
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRow(modifier: Modifier, date: String, onDateChange: (String) -> Unit) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var datePickerState = rememberDatePickerState()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,

        ) {
        // Стрелка влево (левая боковая) - предыдущая дата
        IconButton(
            modifier = Modifier.align(alignment = Alignment.CenterStart),
            onClick = {
                onDateChange(addDay(date, -1))
            }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Назад",
            )
        }
        // Календарь слева
        IconButton(
            modifier = Modifier.align(alignment = Alignment.CenterStart)
                .offset(x = 60.dp),
            onClick = {
                showDatePickerDialog = true
            }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Календарь",
            )
        }
        // Дата по центру
        Text(
            text = date,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        // Стрелка вправо (правая боковая)
        IconButton(
            modifier = Modifier.align(alignment = Alignment.CenterEnd),
            onClick = {
                onDateChange(addDay(date, 1))
            }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Вперед",
            )
        }
    }
    if (showDatePickerDialog) {
        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.LightGray
        )
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateChange(formatter.format(Date(millis)))
                        }
                        showDatePickerDialog = false
                    }, colors = buttonColors,
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("OK", fontSize = 22.sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDatePickerDialog = false
                    }, colors = buttonColors,
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("Отмена", fontSize = 22.sp)
                }
            },
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

fun addDay(date: String, delta: Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = formatter.parse(date)!!  // Parse string to Date
    calendar.add(Calendar.DATE, delta)   // Subtract 1 day
    return formatter.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val fakeDishes = listOf(
        Dish(dishName = "омлет", pro = 10.0, fat = 10.0, carbs = 10.0, cal = 10.0),
        Dish(dishName = "макароны", pro = 10.0, fat = 10.0, carbs = 10.0, cal = 10.0),
    )
    val fakeMeals = listOf(
        Meal(dishId = fakeDishes[0].id, weight = 200.0, pro = 20.0, fat = 20.0, carbs = 20.0, cal = 10.0),
        Meal(mealDate = "2026-02-12", dishId = fakeDishes[1].id, weight = 100.0, pro = 10.0, fat = 10.0, carbs = 10.0, cal = 10.0)
    )
    var date = "2026-02-12"
    MainContent(navController = navController, meals = fakeMeals, dishes = fakeDishes,
        date = date, onDateChange = {}, onDelete = {})
}