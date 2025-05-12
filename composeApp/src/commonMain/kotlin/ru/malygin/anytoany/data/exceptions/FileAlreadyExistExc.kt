package ru.malygin.anytoany.data.exceptions

class FileAlreadyExistExc() : Exception(tempMessage) {//TODO: добавить переводы для ошибки
    companion object{
        val tempMessage = "Файл с таким названием уже существует"
    }
}