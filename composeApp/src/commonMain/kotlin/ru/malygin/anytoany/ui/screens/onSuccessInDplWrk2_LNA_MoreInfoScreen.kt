@file:OptIn(ExperimentalUuidApi::class)
@file:Suppress("FunctionName")

package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.screenModule
import ru.malygin.anytoany.data.dtos.FacilityDevice
import ru.malygin.anytoany.data.dtos.FacilityDeviceHeader
import ru.malygin.anytoany.data.dtos.IpUtils.toPorts
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.utils.FacilityUtils
import ru.malygin.anytoany.data.view_models.LocalNetworkAnalyseModel
import ru.malygin.anytoany.ui.cmp.trustedBlock
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun onSuccessInDplWrk2_LNA_MoreInfoScreen(
    screenModel: LocalNetworkAnalyseModel
) {
    val state = screenModel.selectedFacilityDevice.collectAsState()

    if (state.value == null) onEmptyDevice()
    else onSuccess(
        device = state.value!!,
        onReselectDevice = {
            screenModel.pushSelectedFacilityDevice(it)
        }
    )

}

@Composable
private fun onEmptyDevice(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Устройство не выбрано")
    }
}

@Composable
private fun onSuccess(
    device: FacilityDeviceHeader,
    onReselectDevice: (fd: FacilityDeviceHeader) -> Unit
){
    val oDa = OnSuccessUiComponents(device)
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                trustedBlock(
                    device.isTrusted,
                    paddingValues = PaddingValues(top = 4.dp, bottom = 20.dp)
                )
            }

            item {
                oDa.uiMainInfo()
                HorizontalDivider(thickness = 6.dp, color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                oDa.uiPRG_Version()
                HorizontalDivider(thickness = 6.dp, color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                oDa.uiRealConnectedTo(
                    onReselectDevice = {
                         onReselectDevice(it)
                    }
                )
                HorizontalDivider(thickness = 6.dp, color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                oDa.uiVirtualConnectedProtocol()
            }
        }
    }
}

private class OnSuccessUiComponents(
    d: FacilityDeviceHeader
){
    private val currentDevice = d

    private val stdModifier = Modifier
        .fillMaxWidth()

    private val uiTextModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp)
        .background(Color.Cyan)
        .padding(horizontal = 4.dp)

    @Composable
    private fun uiUiText(modifier: Modifier = uiTextModifier,text: String) =
        Text(
            modifier = modifier,
            text = text
        )

    @Composable
    fun uiMainInfo(
        modifier: Modifier = Modifier
    ){
        Column(
            modifier = stdModifier
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                uiUiText(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    text = currentDevice.dv_info.getUiName()
                )
                Text(
                    color = Color.Gray,
                    fontSize = 12.sp,
                    text = if(currentDevice.isActive) "активно" else "не активно"
                )
            }
            uiUiText(
                text = currentDevice.dv_id.toString()
            )
            uiUiText(
                text = currentDevice.dv_info.getUiNetInfo().getUiMAC()
            )
            uiUiText(
                text = currentDevice.dv_info.getUiNetInfo().getUiIPs().joinToString(","){it.ip}
            )
        }
    }

    @Composable
    fun uiPRG_Version(
        modifier: Modifier = Modifier
    ){
        val osV = currentDevice.dv_os
        val prgV = currentDevice.dv_programs
        Column(
            modifier = stdModifier.then(modifier)
        ){
            val os_Name = buildAnnotatedString {
                append("Операционная система: ")
                withStyle(SpanStyle(background = Color.Cyan)){
                    append(osV.productName)
                }
            }
            val os_Ver = buildAnnotatedString {
                append("Версия ОС: ")
                withStyle(SpanStyle(background = Color.Cyan)){
                    append(osV.productVersion)
                }
            }
            Text(os_Name)
            Text(os_Ver)

            prgV?.let {
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Программное обеспечение"
                )
                it.forEachIndexed {ind, item->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("Название: ${item.productName}")
                        Text("Версия: ${item.productVersion}")
                    }
                    if (ind != it.size - 1)
                        HorizontalDivider()
                }
            }
        }
    }

    @Composable
    fun uiRealConnectedTo(
        modifier: Modifier = Modifier,
        onReselectDevice: (fd: FacilityDeviceHeader) -> Unit
    ){
        val rConnection = currentDevice.dv_info.getUiRealConnections()

        Column(
            modifier = stdModifier
        ){
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = "Подключено к"
            )

            rConnection.forEach { item->
                val connectToDevice = FacilityUtils.getFacilityDeviceByIp(item._connectIn.to)
                connectToDevice?.let {dv->
                    uiUiText(
                        text = dv.dv_info.getUiName()
                    )
                    uiUiText(
                        text = dv.dv_id.toString()
                    )
                    uiUiText(
                        text = dv.dv_info.getUiNetInfo().getUiMAC()
                    )

                    Button(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        onClick = {
                            onReselectDevice(dv)
                        }
                    ){
                        Text(text = "Подробнее")
                    }
                }
            }
        }
    }

    @Composable
    fun uiVirtualConnectedProtocol(
        modifier: Modifier = Modifier
    ){
        val allowedPorts = currentDevice.dv_info.getUiNetInfo().getUiIPs()
        Column(
            modifier = stdModifier
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = "Разрешенные порты"
            )
            allowedPorts.forEach { item->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    uiUiText(
                        modifier = Modifier,
                        text = item.ip
                    )
                    uiUiText(
                        modifier = Modifier,
                        text = item.allowedPorts.toPorts()
                    )
                }
                HorizontalDivider()
            }
        }
    }
}