package ru.malygin.anytoany.data.exceptions

class TokenIsNullExc() : Exception(tempMessage)  {

    companion object{
        val tempMessage = "Токен не передан"
    }
}