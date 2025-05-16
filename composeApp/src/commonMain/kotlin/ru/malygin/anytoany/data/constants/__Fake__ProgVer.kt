@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

import ru.malygin.anytoany.data.data_cls.Product_Version_Save

object __Fake__ProgVer {

    private val _0 = defaultProgram("Текстовый редактор","1.0.3")
    private val _1 = defaultProgram("Фоторедактор","0.9.3")
    private val _2 = defaultProgram("Фоторедактор","1.2.1")
    private val _3 = defaultProgram("Текстовый редактор","0.7.9")
    private val _4 = defaultProgram("Internet Explorer","11.0.9600.18617")
    private val _5 = defaultProgram("FTP Client","5.1.0")

    val windowsList_1 = listOf(_0, _1, _5)
    val windowsList_2 = listOf(_2, _3)

    val windowsServerList_1 = listOf(_0, _4)

    val machineList_1 = listOf(_5)

    private fun defaultProgram(
        name:String = "Блокнот",
        version:String = "1.0.3"
    ) = Product_Version_Save(
        productName = name,
        productVersion = version
    )
}