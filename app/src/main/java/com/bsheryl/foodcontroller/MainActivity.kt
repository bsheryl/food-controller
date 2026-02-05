@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.bsheryl.foodcontroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting(name = "Android")
//            DatePickerScreen()
        }
    }
}

val formatter = SimpleDateFormat("yyyy-MM-dd")

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(formatter.format(Date()))}
    val datePickerState = rememberDatePickerState()
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("FoodController", fontSize = 22.sp)},
                navigationIcon = { IconButton({}) { Icon(Icons.Filled.Menu, contentDescription = "Меню") }},
                actions = {
                    IconButton({}) { Icon(Icons.Filled.Person, contentDescription = "Профиль") }
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
                    modifier = Modifier.align(alignment = Alignment.CenterStart).offset(x = 60.dp),
                    onClick = { showDialog = true
                }) { Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Календарь",
                )}
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
            Text(text ="Здесь будут каллории", fontSize = 28.sp, modifier = Modifier.padding(it))
            Text(text ="Здесь будут блюда", fontSize = 28.sp, modifier = Modifier.padding(it))
        }
        if (showDialog) {
            val buttonColors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.DarkGray, contentColor = androidx.compose.ui.graphics.Color.LightGray)
            DatePickerDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let {millis ->
                            date = formatter.format(Date(millis))
                        }
                        showDialog = false
                    }, colors = buttonColors,
                        border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color.LightGray)) {
                        Text("OK", fontSize = 22.sp)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }, colors = buttonColors,
                        border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color.LightGray)) {
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

fun addDay(date: String, delta: Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = formatter.parse(date)!!  // Parse string to Date
    calendar.add(Calendar.DATE, delta)   // Subtract 1 day
    return formatter.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting(name = "Android")
}