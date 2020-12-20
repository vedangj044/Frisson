package com.vedangj044.frisson

fun getApproximateValue(number: Int): String {

    return when {
        number < 1000 -> {
            number.toString() //for number less than one thousand
        }
        number < 1000000 -> {
            (number / 1000).toString() + "K" //for number less than a million
        }
        number < 1000000000 -> {
            (number / 1000000).toString() + "M" //for number less than a billion
        }
        number < 1000000000000 -> {
            (number / 1000000000).toString() + "B" //for number less than a trillion
        }
        else -> (number / 1000000000000).toString() + "T"
    }
}