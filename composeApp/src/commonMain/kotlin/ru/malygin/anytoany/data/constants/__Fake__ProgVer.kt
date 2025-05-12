@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

import ru.malygin.anytoany.data.data_cls.Product_Version_Save

object __Fake__ProgVer {

    fun defaultProgram(
        name:String = "Блокнот",
        version:String = "1.0.3"
    ) = Product_Version_Save(
        productName = name,
        productVersion = version
    )
}