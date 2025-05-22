@file:Suppress("ClassName")
package ru.malygin.anytoany.data.data_cls

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("product_version_save")
data class Product_Version_Save(
    val productName: String,
    val productVersion: String
)
