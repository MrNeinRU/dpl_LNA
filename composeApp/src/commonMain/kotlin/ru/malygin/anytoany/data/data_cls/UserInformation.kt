package ru.malygin.anytoany.data.data_cls

data class UserInformation(
    val id: String,
    val name: String,
    val post: String,
    val email: String,
    val phone: String,
    val image: String? = null,
    val password: String
)
