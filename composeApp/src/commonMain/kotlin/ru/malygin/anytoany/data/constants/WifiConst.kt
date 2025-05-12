@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

object WifiConst {

    enum class ChannelWidth_(
        /**
         * ScanResult.CHANNEL_WIDTH_20MHZ [[Android]]
         */
        val androidId: Int,

        val realValue: Int
    ){
        CHANNEL_WIDTH_20MHZ(
            androidId = 0,
            realValue = 20
        ),
        CHANNEL_WIDTH_40MHZ(
            androidId = 1,
            realValue = 40
        ),
        CHANNEL_WIDTH_80MHZ(
            androidId = 2,
            realValue = 80
        ),
        CHANNEL_WIDTH_160MHZ(
            androidId = 3,
            realValue = 160
        ),
        CHANNEL_WIDTH_80MHZ_PLUS_MHZ(
            androidId = 4,
            realValue = 80
        ),
        CHANNEL_WIDTH_320MHZ(
            androidId = 5,
            realValue = 320
        )
    }

    enum class WiFiGHZ(
        val normalName: String
    ){
        _2ghz(
            normalName = "2.4 GHz"
        ),
        _5ghz(
            normalName = "5 GHz"
        ),
        _6ghz(
            normalName = "6 GHz"
        )
    }
}