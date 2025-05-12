@file:OptIn(ExperimentalUuidApi::class)

package ru.malygin.anytoany.ui.screens.tab_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lan
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.malygin.anytoany.data.dtos.FacilityDeviceHeader
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.view_models.LocalNetworkAnalyseModel
import ru.malygin.anytoany.ui.screens.onSuccessInDplWrk2
import ru.malygin.anytoany.ui.screens.onSuccessInDplWrk2VisualPreviewNewTab
import ru.malygin.anytoany.ui.screens.onSuccessInDplWrk2_LNA_MoreInfoScreen
import kotlin.uuid.ExperimentalUuidApi

class HomeTab(
    val data: NetworkClustDto,
    val onBack: (selectedFacilityDevice: FacilityDeviceHeader) -> Unit
) : Tab{

    override val options: TabOptions
        @Composable
        get() {
            val title = "Главная страница"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        onSuccessInDplWrk2(
            data = data,
            onBack = {
                onBack(it)
            }
        )
    }

}

class VisualPreviewNewTab(
    private val screenModel: LocalNetworkAnalyseModel
): Tab{
    @Composable
    override fun Content() {
        onSuccessInDplWrk2VisualPreviewNewTab(screenModel)
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Визуализация 1"
            val icon = rememberVectorPainter(Icons.Outlined.Lan)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}

class VisualPreviewTab(
    val data: NetworkClustDto,
    val customData: String = "1"
): Tab{
    @Composable
    override fun Content() {
        Text("Визуализация $customData")
//        onSuccessInDplWrk2VisualPreviewTab(data)
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Визуализация $customData"
            val icon = rememberVectorPainter(Icons.Outlined.Hub)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

}

class MoreInfoTab(
    val screenModel: LocalNetworkAnalyseModel
): Tab{
    @Composable
    override fun Content() {
        onSuccessInDplWrk2_LNA_MoreInfoScreen(screenModel = screenModel)
    }

    override val options: TabOptions
        @Composable
        get() {
            val state = screenModel.selectedFacilityDevice.collectAsState()

            val title = "Информация о ${state.value?.dv_info?.getUiName()} ${state.value?.dv_id}"
            val icon = rememberVectorPainter(Icons.Outlined.Info)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}