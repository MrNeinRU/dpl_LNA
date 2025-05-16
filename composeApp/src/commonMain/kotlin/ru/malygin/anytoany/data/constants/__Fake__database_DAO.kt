@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

object __Fake__database_DAO {

    //@SELECT("SELECT * FROM ${__Fake__database_cns.DATABASE_NAME}")
    fun getStorageToken() : String? = "123"

    //@UPSERT()
    suspend fun setStorageToken(token: String): Boolean {
        //doJob()
        return true
    }
}