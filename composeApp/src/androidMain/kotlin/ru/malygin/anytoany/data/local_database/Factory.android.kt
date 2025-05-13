package ru.malygin.anytoany.data.local_database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import ru.malygin.anytoany.data.constants.__Fake__database_cns
import ru.malygin.anytoany.data.local_database.models.MainDatabase

fun createTokenDatabase(
    ctx: Context
): RoomDatabase.Builder<MainDatabase>  {
    val appCtx = ctx.applicationContext
    val dbFile = appCtx.getDatabasePath(__Fake__database_cns.DATABASE_NAME)
    return Room
        .databaseBuilder<MainDatabase>(
            context = appCtx,
            name = dbFile.absolutePath
        )
}