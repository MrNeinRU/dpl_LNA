package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import ovh.plrapps.mapcompose.ui.MapUI
import ru.malygin.anytoany.data.view_models.LocalNetworkAnalyseModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun onSuccessInDplWrk2VisualPreviewNewTab(
    screenModel: LocalNetworkAnalyseModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        MapUI(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    )
                )
                .onPointerEvent(PointerEventType.Scroll){
                    screenModel.scrollZoom(
                        scale = (it.changes.first().scrollDelta.y * -1).toDouble().times(.4)
                    )
                }
            ,
            state = screenModel.mapState
        )
    }
}