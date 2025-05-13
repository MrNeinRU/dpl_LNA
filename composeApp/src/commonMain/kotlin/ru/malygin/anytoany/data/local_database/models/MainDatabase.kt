@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ru.malygin.anytoany.data.local_database.models

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.sun.tools.javac.Main
import ru.malygin.anytoany.data.data_cls.database.TokenEntity
import ru.malygin.anytoany.data.local_database.dao.TokenDao


@Database(
    entities = [
        TokenEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MainDatabase> {
    override fun initialize(): MainDatabase
}