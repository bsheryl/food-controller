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
import com.bsheryl.foodcontroller.utils.CheckInputedValueDouble
import com.bsheryl.foodcontroller.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel) {
    val userName = profileViewModel.userNameState
    val userWeight = profileViewModel.userWeightState
    val userLimitCal = profileViewModel.userLimitCalState
    val userLimitPro = profileViewModel.userLimitProState
    val userLimitFat = profileViewModel.userLimitFatState
    val userLimitCarbs = profileViewModel.userLimitCarbsState
    ProfileContent(navController = navController, userName = userName, userWeight = userWeight,
        userLimitCal = userLimitCal, userLimitPro = userLimitPro, userLimitFat = userLimitFat,
        userLimitCarbs = userLimitCarbs,
        onUserNameChange = { value -> profileViewModel.setUserName(value)},
        onUserWeightChange = { value -> profileViewModel.setUserWeight(value)},
        onUserLimitCalChange = {profileViewModel.setUserLimitCal(it)},
        onUserLimitProChange = {profileViewModel.setUserLimitPro(it)},
        onUserLimitFatChange = {profileViewModel.setUserLimitFat(it)},
        onUserLimitCarbsChange = {profileViewModel.setUserLimitCarbs(it)},
    )
}

@Composable
fun ProfileContent(navController: NavController, userName: String,
                   userWeight: Double = 0.0,
                   userLimitCal: Double = 0.0,
                   userLimitPro: Double = 0.0,
                   userLimitFat: Double = 0.0,
                   userLimitCarbs: Double = 0.0,
                   onUserNameChange: (String) -> Unit,
                   onUserWeightChange: (Double) -> Unit,
                   onUserLimitCalChange: (Double) -> Unit,
                   onUserLimitProChange: (Double) -> Unit,
                   onUserLimitFatChange: (Double) -> Unit,
                   onUserLimitCarbsChange: (Double) -> Unit
) {
    var localUserName by remember { mutableStateOf(userName) }
    var localUserWeight by remember { mutableStateOf(userWeight) }
    var localUserLimitCal by remember { mutableStateOf(userLimitCal) }
    var localUserLimitPro by remember { mutableStateOf(userLimitPro) }
    var localUserLimitFat by remember { mutableStateOf(userLimitFat) }
    var localUserLimitCarbs by remember { mutableStateOf(userLimitCarbs) }
    val numbericKeyboard = KeyboardOptions(keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done)
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Профиль", fontSize = 22.sp) },
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
                        onUserNameChange(localUserName)
                        onUserWeightChange(localUserWeight)
                        onUserLimitCalChange(localUserLimitCal)
                        onUserLimitProChange(localUserLimitPro)
                        onUserLimitFatChange(localUserLimitFat)
                        onUserLimitCarbsChange(localUserLimitCarbs)
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
            ProfileFieldRow(
                field = localUserName,
                onValueChange = { localUserName = it},
                modifier = Modifier.fillMaxWidth().padding(it),
                text = "Имя"
            )
            ProfileFieldNumberRow(
                field = localUserWeight.toString(),
                onValueChange = { localUserWeight = it.toDoubleOrNull() ?: 0.0 },
                keyboardOptions = numbericKeyboard,
                modifier = Modifier.fillMaxWidth(),
                text = "Вес"
            )
            Text("Лимиты", fontSize = 20.sp)
            ProfileFieldNumberRow(
                field = localUserLimitCal.toString(),
                onValueChange = { localUserLimitCal = it.toDoubleOrNull() ?: 0.0 },
                keyboardOptions = numbericKeyboard,
                modifier = Modifier.fillMaxWidth(),
                text = "Калории"
            )
            ProfileFieldNumberRow(
                field = localUserLimitPro.toString(),
                onValueChange = { localUserLimitPro = it.toDoubleOrNull() ?: 0.0 },
                keyboardOptions = numbericKeyboard,
                modifier = Modifier.fillMaxWidth(),
                text = "Белки"
            )
            ProfileFieldNumberRow(
                field = localUserLimitFat.toString(),
                onValueChange = { localUserLimitFat = it.toDoubleOrNull() ?: 0.0 },
                keyboardOptions = numbericKeyboard,
                modifier = Modifier.fillMaxWidth(),
                text = "Жиры"
            )
            ProfileFieldNumberRow(
                field = localUserLimitCarbs.toString(),
                onValueChange = { localUserLimitCarbs = it.toDoubleOrNull() ?: 0.0 },
                keyboardOptions = numbericKeyboard,
                modifier = Modifier.fillMaxWidth(),
                text = "Углеводы"
            )
        }
    }
}

@Composable
fun ProfileFieldRow(
    field: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
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
            label = { Text(text = text) },
            onValueChange = {onValueChange(it)}
        )
    }
}

@Composable
fun ProfileFieldNumberRow(
    field: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var textState by remember(field) {
        mutableStateOf(if (field == "0.0") "" else field)
    }
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = textState,
            modifier = Modifier.padding(10.dp),
            keyboardOptions = keyboardOptions,
            label = { Text(text = text) },
            onValueChange = { newValue ->
                val doubleValue = CheckInputedValueDouble(newValue, {textState = it})
                onValueChange(doubleValue.toString())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    val userName = "Настасья"
    ProfileContent(navController = navController, userName = userName,
        onUserNameChange = {}, onUserWeightChange = {}, onUserLimitCalChange = {},
        onUserLimitProChange = {}, onUserLimitFatChange = {}, onUserLimitCarbsChange = {})
}