package com.bsheryl.foodcontroller.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.NavRoutes
import com.bsheryl.foodcontroller.entities.Dish
import com.bsheryl.foodcontroller.viewmodel.DishViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DishesScreen(navController: NavController, dishViewModel: DishViewModel,
                 date: String?) {
    val dishes by dishViewModel.dishList.observeAsState(emptyList())
    DishesContent(navController, dishes,
        onDelete = {dish -> dishViewModel.deleteDish(dish)},
        date = date ?: SimpleDateFormat("yyyy-MM-dd").format(Date()))
}

@Composable
fun DishesContent(navController: NavController, dishes: List<Dish>, onDelete: (Dish) -> Unit,
                  date: String) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Список блюд", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(it),
                contentAlignment = Alignment.Center
            ) {
                DishesList(dishes = dishes, navController = navController, onDelete = onDelete,
                    date = date)
            }
            // Кнопка добавить прием пищи
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    onClick = {
                        navController.navigate(NavRoutes.DishScreen.route)
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
fun DishesList(dishes: List<Dish>, navController: NavController, onDelete: (Dish) -> Unit,
               date: String) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        //здесь будет поисковик
//        item { DishTitleRow() }
        items(dishes) { dish -> DishRow(dish, navController = navController,
                onDelete = onDelete, date = date)}
    }
}

@Composable
fun DishRow(dish: Dish, navController: NavController, onDelete: (Dish) -> Unit, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .clickable {
                    navController.navigate("mealScreen/${dish.id}/${date}")
                }) {
            Text(dish.dishName, fontSize = 20.sp)
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Text("Б: " + dish.pro.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
                Text("Ж: " + dish.fat.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
                Text("У: " + dish.carbs.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
                Text("К: " + dish.cal.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
            }
        }
        IconButton(onClick = {onDelete(dish)}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить",
                tint = Color.Red
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DishesPreview() {
    val navController = rememberNavController()
    val fakeDishes = listOf(
        Dish(dishName = "омлет", pro = 10.0, fat = 10.0, carbs = 10.0, cal = 10.0),
        Dish(dishName = "макароны", pro = 10.0, fat = 10.0, carbs = 10.0, cal = 10.0),
    )
    DishesContent(navController, fakeDishes, onDelete = {},
        date = SimpleDateFormat("yyyy-MM-dd").format(Date()))
}