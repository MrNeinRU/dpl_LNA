package ru.malygin.anytoany.data.data_cls.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val token: String
)
