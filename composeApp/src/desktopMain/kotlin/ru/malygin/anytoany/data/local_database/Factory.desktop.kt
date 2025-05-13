package ru.malygin.anytoany.data.local_database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.malygin.anytoany.data.constants.__Fake__database_cns
import ru.malygin.anytoany.data.local_database.models.MainDatabase
import java.io.File

fun createTokenDatabase(): RoomDatabase.Builder<MainDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), __Fake__database_cns.DATABASE_NAME)
    return Room
        .databaseBuilder<MainDatabase>(
            name = dbFile.absolutePath
        )
}