package com.bsheryl.foodcontroller.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bsheryl.foodcontroller.managers.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application = application) {
    private val dataStoreManager = DataStoreManager(application)
    var userNameState by mutableStateOf("")
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserName().collect { savedName ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userNameState.isEmpty()) {
                    userNameState = savedName
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserName(value: String) {
        userNameState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserName(value)
        }
    }

    var userWeightState by mutableStateOf(0.0)
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserWeight().collect { savedWeight ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userWeightState == 0.0) {
                    userWeightState = savedWeight
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserWeight(value: Double) {
        userWeightState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserWeight(value)
        }
    }

    var userLimitCalState by mutableStateOf(0.0)
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserLimitCal().collect { savedLimitCal ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userLimitCalState == 0.0) {
                    userLimitCalState = savedLimitCal
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserLimitCal(value: Double) {
        userLimitCalState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserLimitCal(value)
        }
    }

    var userLimitProState by mutableStateOf(0.0)
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserLimitPro().collect { savedLimitPro ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userLimitProState == 0.0) {
                    userLimitProState = savedLimitPro
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserLimitPro(value: Double) {
        userLimitProState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserLimitPro(value)
        }
    }

    var userLimitFatState by mutableStateOf(0.0)
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserLimitFat().collect { savedLimitFat ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userLimitFatState == 0.0) {
                    userLimitFatState = savedLimitFat
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserLimitFat(value: Double) {
        userLimitFatState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserLimitFat(value)
        }
    }

    var userLimitCarbsState by mutableStateOf(0.0)
        private set

    init {
        // 2. Загружаем начальное значение из DataStore один раз при старте
        viewModelScope.launch {
            dataStoreManager.getUserLimitCarbs().collect { savedLimitCarbs ->
                // Обновляем только если поле пустое (первая загрузка)
                if (userLimitCarbsState == 0.0) {
                    userLimitCarbsState = savedLimitCarbs
                }
            }
        }
    }

    // 3. UI вызывает этот метод: состояние меняется мгновенно
    fun setUserLimitCarbs(value: Double) {
        userLimitCarbsState = value
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.setUserLimitCarbs(value)
        }
    }
}