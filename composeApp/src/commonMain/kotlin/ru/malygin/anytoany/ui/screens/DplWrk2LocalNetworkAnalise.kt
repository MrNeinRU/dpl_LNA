@file:OptIn(ExperimentalUuidApi::class)
package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.malygin.anytoany.data.dtos.FacilityDevice
import ru.malygin.anytoany.data.dtos.FacilityDeviceHeader
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.view_models.LocalNetworkAnalyseModel
import ru.malygin.anytoany.ui.cmp.trustedBlock
import ru.malygin.anytoany.ui.screens.tab_navigation.HomeTab
import ru.malygin.anytoany.ui.screens.tab_navigation.MoreInfoTab
import ru.malygin.anytoany.ui.screens.tab_navigation.VisualPreviewNewTab
import ru.malygin.anytoany.ui.screens.tab_navigation.VisualPreviewTab
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun dplWrk2LocalNetworkAnalise(
    screenModel: LocalNetworkAnalyseModel
) {
    val state = screenModel.state.collectAsState()

//    val selectedUnitId: MutableState<String?> = remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .widthIn(0.dp, 1080.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            when(val data = state.value){
                is LocalNetworkAnalyseModel.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "НЕТ ДАННЫХ"
                            )
                            CircularProgressIndicator()
                        }
                    }
                }
                is LocalNetworkAnalyseModel.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Ошибка: ${data.message}"
                            )
                        }
                    }
                }
                is LocalNetworkAnalyseModel.State.Success -> {
                    TabNavigator(HomeTab(data.data) {}){tbNav->
                        Scaffold(
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(it)
                                ) {
                                    CurrentTab()
                                }
                            },
                            bottomBar = {
                                NavigationBar (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                ){
                                    TabNavigationItem(HomeTab(data.data, onBack = {
                                        screenModel.pushSelectedFacilityDevice(it)
                                        tbNav.current = MoreInfoTab(screenModel)
                                    }))
                                    VerticalDivider(thickness = 3.dp)
                                    TabNavigationItem(VisualPreviewNewTab(screenModel))
//                                    VerticalDivider(thickness = 3.dp)
//                                    TabNavigationItem(VisualPreviewTab(data.data, customData = "2"))
                                    VerticalDivider(thickness = 3.dp)
                                    TabNavigationItem(MoreInfoTab(screenModel))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator= LocalTabNavigator.current

    NavigationBarItem(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(
                horizontal = 4.dp,
                vertical = 4.dp
            ),
        selected = tabNavigator.current.key == tab.key,
//        enabled = tabNavigator.current.key != tab.key,
        onClick = {tabNavigator.current = tab},
        icon = {
            Icon(
                tab.options.icon ?: rememberVectorPainter(Icons.Default.ThumbUp),
                contentDescription = tab.options.title
            )
        }
    )
}

@Composable
fun onSuccessInDplWrk2(
    data: NetworkClustDto,
    onBack: (selectedFacilityDevice: FacilityDeviceHeader) -> Unit
) {
    val tabNavigator = LocalTabNavigator.current
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = data.normalizedName,
    )
    HorizontalDivider()

    LazyColumn {
        items(data.devices){fD->
            Card.cardUi(
                fD,
                onMoreInfoClicked = {id->
                    onBack(fD)
                }
            )
            HorizontalDivider()
        }
    }
}

private object Card{

    @Composable
    fun cardUi(
        fD: FacilityDeviceHeader,
        onMoreInfoClicked: (id:Uuid) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                cardUiLogicSwitch(fD)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                trustedBlock(fD.isTrusted)


                Button(
                    onClick = {
                        onMoreInfoClicked(fD.dv_id)
                    }
                ){
                    Text(
                        text = "Подробно"
                    )
                }
            }
        }

    }

    @Composable
    private fun cardUiLogicSwitch(
        fD: FacilityDeviceHeader
    ){
        val name: String
        val uuid: Uuid
        val mac: String
        val ips: String

        when(fD.dv_info) {
            is FacilityDevice.Machine ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.ip
            }
            is FacilityDevice.PC ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.ip
            }
            is FacilityDevice.Router ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.joinToString(",") { it.ip }
            }
            is FacilityDevice.Server ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.joinToString(",") { it.ip }
            }
            is FacilityDevice.Switch ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.joinToString(",") { it.ip }
            }
            is FacilityDevice.UnknownDevice ->{
                name = fD.dv_info.getUiName()
                uuid = fD.dv_id
                mac = fD.dv_info._networkInfo.mac
                ips = fD.dv_info._networkInfo.ip.ip
            }
        }
        cardUiUnited(
            name = name,
            uuid = uuid,
            mac = mac,
            ips = ips
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    @Composable
    private fun cardUiUnited(
        name:String,
        uuid:Uuid,
        mac: String,
        ips:String,
    ){
        Text(text = name)
        Text(text = "uuid: $uuid")
        Text(text = "MAC: $mac")
        Text(text = "IPs: $ips")
    }
}