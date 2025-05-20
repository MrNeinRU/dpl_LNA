package ru.malygin.anytoany.data.exceptions

class UnknownNetworkExc() : Exception(tempMessage)   {

    companion object{
        val tempMessage = "Неизвестная ошибка сети"
    }
}