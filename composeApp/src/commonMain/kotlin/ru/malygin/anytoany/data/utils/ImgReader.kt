package ru.malygin.anytoany.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import anytoany.composeapp.generated.resources.*
import coil3.compose.rememberAsyncImagePainter
import kotlinx.io.Buffer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ru.malygin.anytoany.data.dtos.FacilityDevice
import ru.malygin.anytoany.data.enum_cls.OsType
import ru.malygin.anytoany.getPlatform

@OptIn(ExperimentalResourceApi::class)
object ImgReader {

    suspend fun readImageFrom(
        from: String = "files",
        path: String? = "578f046c2e25715606a9469d.png"
    ): ImageBitmap {
        val rPath = path ?: "df_wr.png"

        val buffer = Res.readBytes("$from/$rPath").let {
            it.decodeToImageBitmap()
        }
        return buffer
    }

    fun readMapLayout(
        from: String = "files",
        path: String? = "mb31917-1_th.jpg"
    ) = TileStreamProvider{row: Int, col: Int, zoomLvl: Int ->
        val rPath = path ?: "df_wr.png"
        try {
            val buffer = Buffer()
            Res.readBytes("$from/$rPath").let {
                buffer.write(it)
                buffer
            }
        } catch (e: Exception) {
            null
        }
    }

    @Composable
    fun getPainterForMapMarker(
        fd: FacilityDevice
    ): Painter {
        return when(getPlatform().osType){
            OsType.ANDROID -> {
                when(fd){
                    is FacilityDevice.Machine -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_machine.svg"))
                    is FacilityDevice.PC -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_pc.svg"))
                    is FacilityDevice.Router -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_router.svg"))
                    is FacilityDevice.Server -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_server.svg"))
                    is FacilityDevice.Switch -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_swith.svg"))
                    is FacilityDevice.UnknownDevice -> rememberAsyncImagePainter(Res.getUri("drawable/map_marker_device_unknown.svg"))
                }
            }
            else -> {
                when(fd){
                    is FacilityDevice.Machine -> painterResource(Res.drawable.map_marker_machine)
                    is FacilityDevice.PC -> painterResource(Res.drawable.map_marker_pc)
                    is FacilityDevice.Router -> painterResource(Res.drawable.map_marker_router)
                    is FacilityDevice.Server -> painterResource(Res.drawable.map_marker_server)
                    is FacilityDevice.Switch -> painterResource(Res.drawable.map_marker_swith)
                    is FacilityDevice.UnknownDevice -> painterResource(Res.drawable.map_marker_device_unknown)
                }
            }
        }
    }
}