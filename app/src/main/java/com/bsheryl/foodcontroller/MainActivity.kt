@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.bsheryl.foodcontroller

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.components.DishScreen
import com.bsheryl.foodcontroller.components.DishesScreen
import com.bsheryl.foodcontroller.viewmodel.DishViewModel
import com.bsheryl.foodcontroller.viewmodel.MealViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                val dishViewModel: DishViewModel = viewModel(
                    it,
                    "DishViewModel",
                    DishViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                val mealViewModel: MealViewModel = viewModel(
                    it,
                    "MealViewModel",
                    MealViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                val navController = rememberNavController()
                NavHost(navController, startDestination = NavRoutes.Main.route) {
                    composable(NavRoutes.Main.route) {
                        Main(
                            name = "Android",
                            navController = navController
                        )
                    }
                    composable(NavRoutes.Dishes.route) { DishesScreen(navController, dishViewModel = dishViewModel) }
                    composable(NavRoutes.DishScreen.route) {
                        DishScreen(
                            navController = navController,
                            dishViewModel = dishViewModel
                        )
                    }
//            DatePickerScreen()
                }
            }
        }
    }
}

val formatter = SimpleDateFormat("yyyy-MM-dd")

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(name: String, modifier: Modifier = Modifier, navController: NavController) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(formatter.format(Date()))}
    val datePickerState = rememberDatePickerState()

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
            Box(
                modifier = Modifier.fillMaxWidth().padding(it),
                contentAlignment = Alignment.Center,

                ) {
                // Стрелка влево (левая боковая) - предыдущая дата
                IconButton(
                    modifier = Modifier.align(alignment = Alignment.CenterStart),
                    onClick = {
                        date = addDay(date, -1)
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
                        date = addDay(date, 1)
                    }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Вперед",
                    )
                }
            }
            //БЖУК
            Box(
                modifier = Modifier.fillMaxWidth().padding(it),
                contentAlignment = Alignment.Center,
            ) {
                Column() {
                    Row(modifier = Modifier.fillMaxWidth()) {
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
                                fontSize = 20.sp,
                                text = "Белки"
                            )
                            Text(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterEnd),
                                fontSize = 20.sp,
                                text = "0/0"
                            )
                        }
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
                                fontSize = 20.sp,
                                text = "Жиры"
                            )
                            Text(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterEnd),
                                fontSize = 20.sp,
                                text = "0/0"
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
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
                                fontSize = 20.sp,
                                text = "Углеводы"
                            )
                            Text(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterEnd),
                                fontSize = 20.sp,
                                text = "0/0"
                            )
                        }
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
                                fontSize = 20.sp,
                                text = "Калории"
                            )
                            Text(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterEnd),
                                fontSize = 20.sp,
                                text = "0/0"
                            )
                        }
                    }
                }
            }
            Text(
                text = "Здесь будут блюда",
                fontSize = 28.sp,
                modifier = Modifier.padding(it)
            )
            // Кнопка добавить прием пищи
            Box(
                modifier = Modifier.fillMaxWidth().padding(it),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    onClick = {
                        navController.navigate(NavRoutes.Dishes.route)
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавление приема пищи",
                    )
                }
            }
        }
        if (showDatePickerDialog) {
            val buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = androidx.compose.ui.graphics.Color.LightGray
            )
            DatePickerDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                date = formatter.format(Date(millis))
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

}

sealed class NavRoutes(val route: String) {
    object Main: NavRoutes("main")
    object Dishes: NavRoutes("dishes")
    object DishScreen: NavRoutes("DishScreen")
}



fun addDay(date: String, delta: Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = formatter.parse(date)!!  // Parse string to Date
    calendar.add(Calendar.DATE, delta)   // Subtract 1 day
    return formatter.format(calendar.time)
}

class DishViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return DishViewModel(application) as T
    }
}

class MealViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(application) as T
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()
    Main(name = "Android", navController = navController)
}

