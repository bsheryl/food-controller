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

@Composable
fun DishesScreen(navController: NavController, dishViewModel: DishViewModel) {
    val dishes by dishViewModel.dishList.observeAsState(emptyList())
    DishesContent(navController, dishes)
}

@Composable
fun DishesContent(navController: NavController, dishes: List<Dish>) {
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
                DishesList(dishes = dishes, navController = navController)
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
fun DishesList(dishes: List<Dish>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        //здесь будет поисковик
//        item { DishTitleRow() }
        items(dishes) { dish -> DishRow(dish, navController = navController)}
    }
}

@Composable
fun DishRow(dish: Dish, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)
        .clickable {
            navController.navigate("mealScreen/${dish.id}")
        }) {
        Text(dish.dishName, fontSize = 20.sp)
        Row(Modifier.fillMaxWidth().padding(5.dp)) {
            Text("Б: " + dish.pro.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
            Text("Ж: " + dish.fat.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
            Text("У: " + dish.carbs.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
            Text("К: " + dish.cal.toString(), Modifier.weight(0.2f), fontSize = 14.sp)
//        Text("Delete", Modifier.weight(0.2f).clickable { delete(user.id) }, color=Color(0xFF6650a4), fontSize = 22.sp)
        }
    }
}

@Composable
fun DishTitleRow() {
    Row(modifier = Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
//        Text("Name", color = Color.White, fontSize = 22.sp)
        Text("Наименование", Modifier.weight(0.4f), fontSize = 14.sp)
        Text("Белки", Modifier.weight(0.2f), fontSize = 14.sp)
        Text("Жиры", Modifier.weight(0.2f), fontSize = 14.sp)
        Text("Углеводы", Modifier.weight(0.2f), fontSize = 14.sp)
        Text("Калории", Modifier.weight(0.2f), fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DishesPreview() {
    val navController = rememberNavController()
    val fakeDishes = listOf(
        Dish("1", "омлет", 10, 10, 10, 10),
        Dish("2", "макароны", 10, 10, 10, 10),
    )
    DishesContent(navController, fakeDishes)
}