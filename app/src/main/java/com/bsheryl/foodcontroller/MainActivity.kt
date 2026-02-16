@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.bsheryl.foodcontroller

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsheryl.foodcontroller.components.DishScreen
import com.bsheryl.foodcontroller.components.DishesScreen
import com.bsheryl.foodcontroller.components.MainScreen
import com.bsheryl.foodcontroller.components.MealScreen
import com.bsheryl.foodcontroller.viewmodel.DishViewModel
import com.bsheryl.foodcontroller.viewmodel.MealViewModel

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
                        MainScreen(navController = navController,
                            mealViewModel = mealViewModel, dishViewModel = dishViewModel
                        )
                    }
                    composable(NavRoutes.Dishes.route + "/{date}") { stackEntry ->
                        val date = stackEntry.arguments?.getString("date")
                        DishesScreen(navController, dishViewModel = dishViewModel, date = date)
                    }
                    composable(NavRoutes.DishScreen.route) {
                        DishScreen(navController = navController, dishViewModel = dishViewModel)
                    }
                    composable(NavRoutes.MealScreen.route + "/{dishId}/{date}") { stackEntry ->
                        val dishId = stackEntry.arguments?.getString("dishId")
                        val date = stackEntry.arguments?.getString("date")
                        MealScreen(
                            navController = navController, dishId = dishId,
                            mealViewModel = mealViewModel, dishViewModel = dishViewModel,
                            date = date
                        )
                    }
//            DatePickerScreen()
                }
            }
        }
    }
}

sealed class NavRoutes(val route: String) {
    object Main: NavRoutes("mainScreen")
    object Dishes: NavRoutes("dishesScreen")
    object DishScreen: NavRoutes("dishScreen")
    object MealScreen: NavRoutes("mealScreen")
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

