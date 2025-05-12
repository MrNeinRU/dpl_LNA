package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import anytoany.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import ru.malygin.anytoany.data.dtos.FacilityDevice
import ru.malygin.anytoany.data.dtos.FacilityDeviceConnections
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.utils.FacilityUtils.getRealConnections
import ru.malygin.anytoany.data.utils.ImgReader
import kotlin.random.Random

//
//@OptIn(ExperimentalResourceApi::class)
//@Composable
//fun onSuccessInDplWrk2VisualPreviewTab(
//    networkClustDto: NetworkClustDto
//){
//    val numberOfDevices = networkClustDto.devices.size
//
//    val offsets: List<Pair<List<String>, Offset>> = networkClustDto.devices.map { facilityDevice->
//        val cstOffset = Offset(
//            x = Random.nextInt(from = 10, until = (1000 - 10)).toFloat(),
//            y = Random.nextInt(from = 10, until = (500 - 10)).toFloat()
//        )
//
//        when(facilityDevice){
//            is FacilityDevice.Machine -> listOf(facilityDevice._networkInfo.ip.ip) to cstOffset
//            is FacilityDevice.PC -> listOf(facilityDevice._networkInfo.ip.ip) to cstOffset
//            is FacilityDevice.Router -> facilityDevice._networkInfo.ip.map { it.ip } to cstOffset
//            is FacilityDevice.Server -> facilityDevice._networkInfo.ip.map { it.ip } to cstOffset
//            is FacilityDevice.Switch -> facilityDevice._networkInfo.ip.map { it.ip } to cstOffset
//            is FacilityDevice.UnknownDevice -> listOf(facilityDevice._networkInfo.ip.ip) to cstOffset
//        }
//    }
//    val realConnectionsPath = networkClustDto.devices.getRealConnections()
//    val offsetsSimple = offsets.map { it.second }
//
//    val crt = rememberCoroutineScope()
//    val textMeasurer = rememberTextMeasurer()
//    var imgTest: ImageBitmap? by remember{ mutableStateOf(null) }
//
//    crt.launch {
//        imgTest = ImgReader.readImageFrom()
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .drawBehind {
//                drawRect(
//                    size = size,
//                    color = Color.Cyan
//                )
//            }
//    ) {
//        Text("s= ${offsetsSimple.size}")
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//        ){
//
//            imgTest?.let {imB->
//                drawPointsOfDevices(
//                    numberOfDevices = numberOfDevices,
//                    imB = imB,
//                    offsets = offsetsSimple
//                )
//            }
//            drawLinesOfRealConnections(
//                offsets = offsets,
//                dv = realConnectionsPath
//            )
////            drawNameOfDevice(
////                offsets = offsets,
////                dv = realConnectionsPath,
////                tm = textMeasurer
////            )
////            drawSaturn()
//        }
//    }
//}
//
//private fun DrawScope.drawNameOfDevice(
//    offsets: List<Pair<List<String>, Offset>>,
//    dv: List<FacilityDeviceConnections>,
//    tm: TextMeasurer
//){
//    offsets.forEach { pair->
//        val anString = buildAnnotatedString {
//            withStyle(
//                style = SpanStyle(
//                    fontSize = 24.sp,
//                    color = Color.Black
//                )
//            ){
//                append(
//                    dv.find {
//                        it._connectIn.from in pair.first
//                    }?._device ?: throw Exception("No device for place text from ${pair.first}")
//                )
//            }
//        }
//        drawText(
//            topLeft = Offset(
//                x = if(pair.second.x<0) pair.second.x * -1 else pair.second.x,
//                y = if(pair.second.y<0) pair.second.y * -1 else pair.second.y
//            ),
//            textMeasurer = tm,
//            text = anString
//        )
//    }
//}
//
//private fun DrawScope.drawLinesOfRealConnections(
//    offsets: List<Pair<List<String>, Offset>>,
//    dv: List<FacilityDeviceConnections>
//){
//    dv.map{it._connectIn}.forEach { conections->
//        drawLine(
//            color = Color.Black,
//            start = offsets.find {
//                conections.from in it.first
//            }?.second ?: throw Exception("No offset for [from] ${conections.from}"),
//            end = offsets.find {
//                conections.to in it.first
//            }?.second ?: throw Exception("No offset for [to] ${conections.to}"),
//        )
//    }
//}
//
//private fun DrawScope.drawPointsOfDevices(
//    center: Offset = Offset(size.width / 2f, size.height / 2f),
//    numberOfDevices: Int,
//    imB: ImageBitmap,
//    offsets: List<Offset>
//){
//    offsets.forEachIndexed { indexOf, offset ->
//        drawImage(
//            image = imB,
//            dstOffset = IntOffset(
//                x = offset.x.toInt(),
//                y = offset.y.toInt()
//            ),
//            dstSize = IntSize(100, 100)
//        )
//    }
//}
//
//private fun DrawScope.drawSaturn(
//    center: Offset = Offset(size.width / 2f, size.height / 2f),
//    outlineStyle_: Stroke = Stroke(width = 8f),
//) {
//    val radiusSSS = 50f
//    val outlineStyle = Stroke(radiusSSS*0.08f)
//
//    drawCircle(
//        center = center,
//        color = Color.White,
//        radius = radiusSSS,
//        style = outlineStyle,
//    )
//
//    drawArc(
//        topLeft = Offset(
//            center.x - radiusSSS * 0.8f,
//            center.y - radiusSSS * 0.8f
//        ),
//        color = Color.White,
//        startAngle = 180f,
//        sweepAngle = 90f,
//        useCenter = false,
//        style =
//            Stroke(
//                width = 2f,
//            ),
//        size = Size(radiusSSS * 1.6f, radiusSSS * 1.6f),
//    )
//
//    rotate(40f, center) {
//        drawArc(
//            color = Color.White,
//            startAngle = 217f,
//            sweepAngle = 285f,
//            useCenter = false,
//            topLeft = Offset(
//                center.x - radiusSSS * 0.5f,
//                center.y - radiusSSS * 1.5f
//            ),
//            style = outlineStyle,
//            size = Size(radiusSSS, radiusSSS * 3f),
//        )
//    }
//}