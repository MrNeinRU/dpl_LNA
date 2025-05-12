package ru.malygin.anytoany

import android.os.Build
import ru.malygin.anytoany.data.enum_cls.OsType

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val osType: OsType = OsType.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()