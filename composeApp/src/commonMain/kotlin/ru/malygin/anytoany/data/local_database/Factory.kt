package ru.malygin.anytoany.data.local_database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import ru.malygin.anytoany.data.local_database.models.MainDatabase

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MainDatabase>
): MainDatabase{
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(false)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}