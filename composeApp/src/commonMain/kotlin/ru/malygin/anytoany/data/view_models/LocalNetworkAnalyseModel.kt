package ru.malygin.anytoany.data.view_models

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.*
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.malygin.anytoany.data.adapters.NetworkingAdapter
import ru.malygin.anytoany.data.adapters.NetworkingState_GetFacilityCluster
import ru.malygin.anytoany.data.constants.__Fake__database_DAO
import ru.malygin.anytoany.data.data_cls.VisualData
import ru.malygin.anytoany.data.dtos.FacilityDevice
import ru.malygin.anytoany.data.dtos.FacilityDeviceHeader
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.dtos.getDeviceById
import ru.malygin.anytoany.data.utils.ImgReader
import ru.malygin.anytoany.ui.screens.tab_navigation.MoreInfoTab
import kotlin.uuid.ExperimentalUuidApi

class LocalNetworkAnalyseModel(
    private val database: __Fake__database_DAO = __Fake__database_DAO,
    private val networkingAdapter: NetworkingAdapter = NetworkingAdapter()
) : StateScreenModel<LocalNetworkAnalyseModel.State>(State.Loading) {

    sealed class State{
        data object Loading: State()
        data class Error(val message:String): State()
        data class Success(val data: NetworkClustDto): State()
    }
    private var job: Job? = null
    private val spec = TweenSpec<Float>(200, easing = FastOutLinearInEasing)

    private val token = database.getStorageToken()

    private var _selectedFacilityDevice = MutableStateFlow<FacilityDeviceHeader?>(null)

    var selectedFacilityDevice = _selectedFacilityDevice.asStateFlow()

    fun pushSelectedFacilityDevice(fd:FacilityDeviceHeader){
        screenModelScope.launch {
            _selectedFacilityDevice.emit(fd)
        }
    }


    init {
        screenModelScope.launch {
            when (val facInfo = networkingAdapter.getFacilityCluster(token)) {
                is NetworkingState_GetFacilityCluster.Error -> mutableState.value = State.Error(facInfo.message.message?:"Error")
                is NetworkingState_GetFacilityCluster.Success -> mutableState.value = State.Success(facInfo.data)
            }

            when(val nCD = mutableState.value){
                is State.Success -> {
                    doVisualRealConnections(nCD, mapState)
                }
                else->Unit
            }
        }
    }


    private val tileStreamProvider = ImgReader.readMapLayout()
    val mapState = MapState(4, 2048, 1024).apply {

        addLayer(tileStreamProvider)
        onMarkerClick { id, x, y ->
            println("marker id: $id, x: $x, y: $y")

            addCallout(
                id = id, x = x, y = y,
                absoluteOffset = DpOffset(0.dp, (-80).dp),
                autoDismiss = id != TAP_TO_DISMISS_ID,
                clickable = id == TAP_TO_DISMISS_ID,
            ){
                val tabNavigator= LocalTabNavigator.current
                onClickTipWithButton {
                    when(val l = state.value){
                        is State.Success -> {
                            l.data.devices.getDeviceById(id)?.let {
                                pushSelectedFacilityDevice(it)
                                tabNavigator.current = MoreInfoTab(this@LocalNetworkAnalyseModel)
                            }
                        }
                        else->Unit
                    }
                }
            }
        }
        onPathClick { id, x, y ->
            println("path id: $id, x: $x, y: $y")

            val firstDv = id.split(DEFAULT_SPLITTER).first()
            val secondDv = id.split(DEFAULT_SPLITTER).last()

            addCallout(
                id = id, x = x, y = y,
                absoluteOffset = DpOffset(0.dp, (-50).dp),
                autoDismiss = id != TAP_TO_DISMISS_ID,
                clickable = id == TAP_TO_DISMISS_ID,
            ){
                when(val l = state.value){
                    is State.Success -> {
                        l.data.devices.getDeviceById(firstDv)?.let {dvF->
                            l.data.devices.getDeviceById(secondDv)?.let{dvS->
                                onClickTip(
                                    text = "test ${dvF.dv_info.getUiName()} ${dvS.dv_info.getUiName()}"
                                )
                            }
                        }
                    }
                    else->Unit
                }
            }
        }
    }

    fun scrollZoom(
        scale: Double,
    ){
        job?.cancel()

        with(mapState){
            job = screenModelScope.launch {
                println("scale = ${this@with.scale}")
                scrollTo(
                    x = this@with.centroidX,
                    y = this@with.centroidY,
                    destScale = this@with.scale.plus(scale),
                    animationSpec = spec
                )
            }
        }
    }


    override fun onDispose() {
        println("LocalNetworkAnalyseModel is destroyed")
    }

    companion object{
        const val TAP_TO_DISMISS_ID = "tap_to_dismiss_id"
        const val DEFAULT_SPLITTER = "_to_"
    }
}

@OptIn(ExperimentalUuidApi::class)
private fun doVisualRealConnections(
    nCD: LocalNetworkAnalyseModel.State.Success,
    mapState: MapState
){
    val pathsList = mutableListOf<Pair<VisualData, VisualData>>()

    val a123 = nCD.data.devices.map {
        Pair(it, it.dv_info.getUiRealConnections())
    }.sortedBy { it.second.size }

    val grpA123 = a123.groupBy {
        //изменяем уровни для лучшего отображения
        when(it.first.dv_info){
            is FacilityDevice.UnknownDevice -> it.second.size+3
            is FacilityDevice.Router -> it.second.size+1
            else-> it.second.size
        }
    }
    val levels = grpA123.size
    val doubleLevels = 1.0 / (levels+1)
    var initDouble = 1.0

    val _mp = mutableListOf<VisualData>()

    //генерируем маркеры
    grpA123.entries.forEach { initMap->
        val levelsX = initMap.value.size
        val doubleLevelsX = 1.0 / (levelsX+1)
        var initDoubleX = 1.0

        initDouble -= doubleLevels
        val ext= initMap.value.map { a->
            initDoubleX -= doubleLevelsX
            VisualData(
                offset = Offset(initDoubleX.toFloat(), initDouble.toFloat()),
                facilityDeviceHeader = a.first
            )
        }

        _mp.addAll(ext)
    }
    //генерируем пути
    _mp.forEach { vd->
        vd.facilityDeviceHeader.dv_info.getUiRealConnections().forEach { c->
            _mp.find { dd->
                c._connectIn.to in dd.facilityDeviceHeader.dv_info.getUiNetInfo().getUiIPs().map { it.ip }
            }?.let {
                val toPathsList = Pair(vd, it)
                when{
                    toPathsList.second in pathsList.map { y-> y.first } -> Unit
                    else-> pathsList.add(toPathsList)
                }
            }
        }
    }

    println("a123: ${a123.size} | levels: $levels | grp: ${grpA123.size} | doubleLevels: $doubleLevels")
    println(_mp)
    println(pathsList)

    //устанавливаем маркеры
    _mp.forEach { b->
        mapState.addMarker(
            id = "${b.facilityDeviceHeader.dv_id}",
            x = b.offset.x.toDouble(),
            y = b.offset.y.toDouble(),
            clickable = true
        ){
            testMarkerIcon(b.facilityDeviceHeader.dv_info.getUiName(), b)

        }
    }
    //устанавливаем пути
    pathsList.forEach { path->
        mapState.addPath(
            id = "${path.first.facilityDeviceHeader.dv_id}${LocalNetworkAnalyseModel.DEFAULT_SPLITTER}${path.second.facilityDeviceHeader.dv_id}",
            color = Color(0xFF448AFF).copy(alpha = .7f),
            fillColor = Color(0xFF448AFF).copy(alpha = .3f),
            clickable = false//todo true
        ){
            addPoint(path.first.offset.x.toDouble(), path.first.offset.y.toDouble())
            addPoint(path.second.offset.x.toDouble(), path.second.offset.y.toDouble())
        }
    }
}


@Composable
private fun testMarkerIcon(type:String, vd: VisualData){

    val iconIn:Painter =
        ImgReader.getPainterForMapMarker(vd.facilityDeviceHeader.dv_info)

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = iconIn,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(type)
    }
}

@Composable
private fun onClickTipWithButton(
    onDo: () -> Unit
){
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color(209, 156, 31))
            .padding(8.dp)
    ){
        Button(
            onClick = {onDo()}
        ){
            Text("Подробнее")
        }
    }
}
@Composable
private fun onClickTip(
    text: String? = null
){
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color(209, 156, 31))
            .padding(8.dp)
    ){
        text?.let {
            Text(text)
        }?: run {
            Text("нет данных")
        }
    }
}