package com.bsheryl.foodcontroller.managers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class DataStoreManager(private val context: Context) {
    //универсальный метод для сохранения данных
    suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        context.dataStore.data.map { settings ->
            settings[key] ?: defaultValue
        }

    private val USER_NAME = stringPreferencesKey("user_name")
    suspend fun setUserName(value: String) = save(USER_NAME, value)
    fun getUserName(): Flow<String> = read(USER_NAME, "")

    private val USER_WEIGHT = doublePreferencesKey("user_weight")
    suspend fun setUserWeight(value: Double) = save(USER_WEIGHT, value)
    fun getUserWeight(): Flow<Double> = read(USER_WEIGHT, 0.0)

    private val USER_LIMIT_CAL = doublePreferencesKey("user_limit_cal")
    suspend fun setUserLimitCal(value: Double) = save(USER_LIMIT_CAL, value)
    fun getUserLimitCal(): Flow<Double> = read(USER_LIMIT_CAL, 0.0)

    private val USER_LIMIT_PRO = doublePreferencesKey("user_limit_pro")
    suspend fun setUserLimitPro(value: Double) = save(USER_LIMIT_PRO, value)
    fun getUserLimitPro(): Flow<Double> = read(USER_LIMIT_PRO, 0.0)

    private val USER_LIMIT_FAT = doublePreferencesKey("user_limit_fat")
    suspend fun setUserLimitFat(value: Double) = save(USER_LIMIT_FAT, value)
    fun getUserLimitFat(): Flow<Double> = read(USER_LIMIT_FAT, 0.0)

    private val USER_LIMIT_CARBS = doublePreferencesKey("user_limit_carbs")
    suspend fun setUserLimitCarbs(value: Double) = save(USER_LIMIT_CARBS, value)
    fun getUserLimitCarbs(): Flow<Double> = read(USER_LIMIT_CARBS, 0.0)
}