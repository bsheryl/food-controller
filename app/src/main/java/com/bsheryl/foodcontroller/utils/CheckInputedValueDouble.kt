package com.bsheryl.foodcontroller.utils

fun CheckInputedValueDouble(inputedValue: String, textStateOnChange: (String) -> Unit): Double {
    val filtered = inputedValue.replace(',', '.')
        .filterIndexed { index, char ->
            char.isDigit() || (char == '.' && inputedValue.indexOf('.') == index)
        }

    // Обновляем визуальный текст (здесь точка или удаленный .0 не вернутся сами)
//    textState = filtered
    textStateOnChange(filtered)

    // Отправляем число "наверх" в объект Dish
    val doubleValue = filtered.toDoubleOrNull() ?: 0.0
    return doubleValue
}