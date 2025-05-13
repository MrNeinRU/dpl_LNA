package ru.malygin.anytoany.data.local_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.malygin.anytoany.data.data_cls.database.TokenEntity

@Dao
interface TokenDao {
    @Upsert
    suspend fun upsertToken(item: TokenEntity)

    @Query("SELECT * FROM TokenEntity")
    fun getAllAsFlow(): Flow<List<TokenEntity>>
}