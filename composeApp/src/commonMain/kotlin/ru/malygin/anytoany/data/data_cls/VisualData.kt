package ru.malygin.anytoany.data.data_cls

import androidx.compose.ui.geometry.Offset
import ru.malygin.anytoany.data.dtos.FacilityDeviceHeader

data class VisualData(
    val offset: Offset,
    val facilityDeviceHeader: FacilityDeviceHeader,
)
