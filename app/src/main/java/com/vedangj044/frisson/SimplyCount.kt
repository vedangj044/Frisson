package com.vedangj044.frisson

class SimplyCount {

    public fun getApproximateValue(number : Long) : String{

        var convertedNumber = ""

        if(number < 1000){
            convertedNumber = number.toString() //for number less than one thousand
        }
        else if (number < 1000000){
            convertedNumber = (number/1000).toString() + "K" //for number less than a million
        }
        else if(number < 1000000000){
            convertedNumber = (number/1000000).toString() + "M" //for number less than a billion
        }
        else if(number < 1000000000000){
            convertedNumber = (number/1000000000).toString() + "B" //for number less than a trillion
        }
        else
            convertedNumber = (number/1000000000000).toString() + "T" //for number equal to or more than a trillion

        return convertedNumber
    }
}